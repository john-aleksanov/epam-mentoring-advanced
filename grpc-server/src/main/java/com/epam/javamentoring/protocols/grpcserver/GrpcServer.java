package com.epam.javamentoring.protocols.grpcserver;

import com.epam.javamentoring.protocols.grpcserver.ping.PingService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

	private Server server;

	public static void main(String[] args) throws IOException, InterruptedException {
		GrpcServer grpcServer = new GrpcServer();
		grpcServer.startServer();
		System.out.println("Server started");
		grpcServer.blockUntilShutdown();
	}

	public void startServer() throws IOException {
		server = ServerBuilder.forPort(50051)
				.addService(new PingService())
				.build()
				.start();
	}

	public void blockUntilShutdown() throws InterruptedException {
		 if (server != null) {
			 server.awaitTermination();
		 }
	}
}
