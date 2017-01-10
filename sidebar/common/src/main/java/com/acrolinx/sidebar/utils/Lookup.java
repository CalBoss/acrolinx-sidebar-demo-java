/*
 * Copyright (c) 2016-2017 Acrolinx GmbH
 */

package com.acrolinx.sidebar.utils;

import com.acrolinx.sidebar.pojo.document.AcrolinxMatch;
import com.acrolinx.sidebar.pojo.document.AcrolinxMatchWithReplacement;
import org.apache.commons.lang.math.IntRange;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("WeakerAccess") public class Lookup
{
    final static private DiffMatchPatch DIFFER = new DiffMatchPatch();

    protected static LinkedList<OffsetAlign> createOffsetMappingArray(LinkedList<DiffMatchPatch.Diff> diffs)
    {
        LinkedList<OffsetAlign> offsetMapping = new LinkedList<>();
        final AtomicInteger offsetCountOld = new AtomicInteger(0);
        final AtomicInteger currentDiffOffset = new AtomicInteger(0);
        diffs.forEach((diff) -> {
            int offsetCountOldInt = offsetCountOld.get();
            int currentDiffOffsetInt = currentDiffOffset.get();
            int diffLengths = diff.text.length();
            switch (diff.operation) {
                case DELETE:
                    offsetCountOld.set(offsetCountOldInt + diffLengths);
                    currentDiffOffset.set(currentDiffOffsetInt - diffLengths);
                    break;
                case INSERT:
                    currentDiffOffset.set(currentDiffOffsetInt + diffLengths);
                    break;
                case EQUAL:
                    offsetCountOld.set(offsetCountOldInt + diffLengths);
                    break;
            }
            offsetMapping.add(new OffsetAlign(offsetCountOld.get(), currentDiffOffset.get()));
        });
        return offsetMapping;
    }

    protected static Optional<IntRange> getCorrectedMatch(String checkedText, String changedText, int offsetStart,
            int offsetEnd)
    {
        LinkedList<DiffMatchPatch.Diff> diffs = getDiffs(checkedText, changedText);
        LinkedList<OffsetAlign> aligns = createOffsetMappingArray(diffs);
        Optional<OffsetAlign> first = aligns.stream().filter((a) -> a.getOldPosition() >= offsetEnd).findFirst();
        if (first.isPresent()) {
            int index = aligns.indexOf(first.get());
            if (index > 0 && aligns.get(index - 1).getOldPosition() <= offsetStart
                    && diffs.get(index).operation == DiffMatchPatch.Operation.EQUAL) {
                final int diffOffset = aligns.get(index).getDiffOffset();
                return Optional.of(new IntRange(offsetStart + diffOffset, offsetEnd + diffOffset));
            }
            if (index == 0 && diffs.get(0).operation == DiffMatchPatch.Operation.EQUAL) {
                return Optional.of(new IntRange(offsetStart, offsetEnd));
            }
        }
        return Optional.empty();
    }

    protected static LinkedList<DiffMatchPatch.Diff> getDiffs(String checkedText, String changedText)
    {
        DiffMatchPatch differ = new DiffMatchPatch();
        differ.diffTimeout = 5;
        LinkedList<DiffMatchPatch.Diff> diffs = differ.diffMain(checkedText, changedText);
        //differ.diffCleanupSemantic(diffs);
        differ.diffCleanupSemanticLossless(diffs);
        return diffs;
    }

    public static Optional<IntRange> getCorrectedRangeFromAcrolinxMatch(List<AcrolinxMatch> matches, String checkedText,
            String changedText)
    {
        int startOffset = matches.get(0).getRange().getMinimumInteger();
        int endOffset = matches.get(matches.size() - 1).getRange().getMaximumInteger();
        return getCorrectedMatch(checkedText, changedText, startOffset, endOffset);
    }

    public static Optional<IntRange> getCorrectedRangeFromAcrolinxMatchWithReplacement(
            List<AcrolinxMatchWithReplacement> matches, String checkedText, String changedText)
    {
        int startOffset = matches.get(0).getRange().getMinimumInteger();
        int endOffset = matches.get(matches.size() - 1).getRange().getMaximumInteger();
        return getCorrectedMatch(checkedText, changedText, startOffset, endOffset);
    }
}
