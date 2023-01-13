package io.github.thatusualguy.chessonline;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Grpc {
	private static ManagedChannel sManagedChannel;

	static public ManagedChannel getManagedChannel() {
		if (sManagedChannel == null)
			sManagedChannel = ManagedChannelBuilder.forAddress("10.0.2.2", 7105).usePlaintext().build();
		return sManagedChannel;
	}
}
