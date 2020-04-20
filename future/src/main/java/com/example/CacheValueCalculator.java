package com.example;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.function.Function;

public class CacheValueCalculator<K, V> {

	private Map<K, V> map;

	public CacheValueCalculator() {
		this.map = new ConcurrentHashMap<K, V>();
	}

	public Future<V> compute(final K k, Function<K, V> f) {
		CompletableFuture<V> completableFuture = CompletableFuture.supplyAsync(() -> map.computeIfAbsent(k, f));
		return completableFuture;
	}

}
