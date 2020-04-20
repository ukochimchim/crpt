package com.example;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.CacheValueCalculator;

public class FutureComputeApplicationTests {

	private static final int KEYS_COUNT = 20;

	private static final int MAX_GENERATED_KEY_BOUND = 10;

	private static final int GENERATED_KEY_ORIGIN = 0;

	private static long EXPECTED_COUNT = 0;

	@Test
	public void shoudBeEquals() throws InterruptedException, ExecutionException {
		CacheValueCalculator<Long, Long> cacheCalculator = new CacheValueCalculator<Long, Long>();
		Random random = new Random();

		System.out.println("Random generating keys");
		List<Long> keys = random.longs(KEYS_COUNT, GENERATED_KEY_ORIGIN, MAX_GENERATED_KEY_BOUND).boxed()
				.collect(Collectors.toList());

		System.out.println(keys.stream().map(key -> key.toString()).collect(Collectors.joining(",")));

		System.out.println("Find dublicates");

		Set<Long> dublicates = keys.stream().collect(
				Collectors.collectingAndThen(Collectors.groupingBy(Function.identity(), Collectors.counting()), map -> {
					map.values().removeIf(cnt -> cnt < 2);
					long sum = map.values().stream().collect(Collectors.summingLong(Long::longValue));
					System.out.println(map.toString());
					System.out.printf("Count of dublicated elements = %d%n", sum);

					EXPECTED_COUNT = map.keySet().size() + (keys.size() - sum);

					return (map.keySet());
				}));

		if (dublicates.isEmpty()) {
			System.out.println("dublicates not found restart program");
			System.exit(1);
		}

		System.out.println(dublicates.stream().map(key -> key.toString()).collect(Collectors.joining(",")));

		AtomicInteger generatorCounter = new AtomicInteger();
		Function<Long, Long> generator = (key) -> {
			generatorCounter.incrementAndGet();
			System.out.printf("generating value for key=%d%n", key);
			return random.nextLong() % MAX_GENERATED_KEY_BOUND + key;
		};

		System.out.println("Call compute calculator");
		List<Future<Long>> tasks = keys.parallelStream().collect(ArrayList<Future<Long>>::new, (buffer, key) -> {
			buffer.add(cacheCalculator.compute(key, generator));
		}, ArrayList<Future<Long>>::addAll);

		CompletableFuture<Void> allFutures = CompletableFuture
				.allOf(tasks.toArray(new CompletableFuture[tasks.size()]));

		CompletableFuture<Long> result = allFutures.thenApply(v -> {
			return generatorCounter.longValue();
		});

		long proccedGeneratorCounter = result.get();
		System.out.printf("Generator called = key=%d%n", proccedGeneratorCounter);

		Assert.assertEquals(EXPECTED_COUNT, proccedGeneratorCounter);
	}

}
