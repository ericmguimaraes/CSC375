package assignment2.library;

import org.openjdk.jmh.annotations.GenerateMicroBenchmark;

public class Benchmark {

	@GenerateMicroBenchmark
	public void sychronizedServer(){
		SynchronizedLibrary synchronizedGameServer = new SynchronizedLibrary();
	}
	
	@GenerateMicroBenchmark
	public void noJDKServer(){
		NoJDKLibrary noJDKGameServer = new NoJDKLibrary();
	}
	
}
