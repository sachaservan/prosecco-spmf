package prosecco;

import prefixspan.SequentialPatterns;

public interface ProseccoCallbacks {
	   
    public void blockUpdate(SequentialPatterns patterns, int numTransactionsProcessed, long blockRuntime, double blockErrorBound);
}

