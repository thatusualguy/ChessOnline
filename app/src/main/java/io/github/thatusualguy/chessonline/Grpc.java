package io.github.thatusualguy.chessonline;


import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

public class Grpc {
	private static Channel sChannel;

	protected static String jwt = "";

	static public Channel getChannel() {
		if (sChannel == null) {
			ClientInterceptor interceptor = new HeaderClientInterceptor();
			Channel channel = ManagedChannelBuilder.forAddress("10.0.2.2", 7105).usePlaintext().build();
			sChannel = ClientInterceptors.intercept(channel, interceptor);
		}
		return sChannel;
	}

	public static void setJwt(String _jwt) {
		jwt = _jwt;
	}


	public static class HeaderClientInterceptor implements ClientInterceptor {
		@Override
		public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
																   CallOptions callOptions, Channel next) {

			return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

				@Override
				public void start(Listener<RespT> responseListener, Metadata headers) {
					Metadata fixedHeaders = new Metadata();
					Metadata.Key<String> key =
							Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
					fixedHeaders.put(key, Grpc.jwt);
					headers.merge(fixedHeaders);

					super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
						@Override
						public void onHeaders(Metadata headers) {
							super.onHeaders(headers);
						}
					}, headers);
				}
			};
		}
	}
}

