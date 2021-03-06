/*
 * Copyright (c) 2018 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */

package org.mockito.internal.verification.within;

import org.mockito.internal.exceptions.VerificationAwareInvocation;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.MatchableInvocation;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.internal.exceptions.Reporter.noMoreInteractionsWanted;
import static org.mockito.internal.exceptions.Reporter.wantedButNotInvoked;
import static org.mockito.internal.verification.within.VerificationResult.GIVE_ME_THE_NEXT_INVOCATION;

public class Only implements VerificationStrategy {

    private final List<VerificationAwareInvocation> matchingInvocations = new LinkedList<VerificationAwareInvocation>();

    @Override
    public VerificationResult verifyMatchingInvocation(Invocation invocation, MatchableInvocation wanted) {
        if (!matchingInvocations.isEmpty()) {
            throw noMoreInteractionsWanted(invocation, matchingInvocations);
        }

        matchingInvocations.add((VerificationAwareInvocation) invocation);

        return GIVE_ME_THE_NEXT_INVOCATION;
    }

    @Override
    public VerificationResult verifyNotMatchingInvocation(Invocation invocation, MatchableInvocation wanted) {
        throw noMoreInteractionsWanted(invocation, matchingInvocations);
    }

    @Override
    public void verifyAfterTimeElapsed(MatchableInvocation wanted) {
        if (matchingInvocations.isEmpty()) {
            throw wantedButNotInvoked(wanted);
        }
    }
}
