/** Copyright 2015 Goshi Noguchi (noggon54@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. */

package org.nognog.gdx.jmatcher.requester;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

/**
 * @author goshi 2015/12/10
 * @param <T>
 *            type of main returned object
 */

public abstract class Requester<T> implements Runnable {
	private final String host;
	private final int port;
	private final SocketHints hints;
	private final int retryCount;
	protected final ResponseListener<T> listener;

	/**
	 * @param host
	 * @param port
	 * @param hints
	 * @param retryCount
	 * @param listener 
	 */
	public Requester(String host, int port, SocketHints hints, int retryCount, ResponseListener<T> listener) {
		this.host = host;
		this.port = port;
		this.hints = hints;
		this.retryCount = retryCount;
		this.listener = listener;
	}

	@Override
	public void run() {
		final Socket socket = Gdx.net.newClientSocket(Protocol.TCP, this.host, this.port, this.hints);
		try {
			for (int i = 0; i < this.retryCount; i++) {
				try {
					final T object = this.executeRequest(socket);
					this.postSuccess(object);
					return;
				} catch (IOException e) {
					// failed to connect to the server
				}
			}
			this.postFailure();
		} finally {
			socket.dispose();
		}
	}
	
	protected void postSuccess(final T success) {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				Requester.this.listener.success(success);
			}
		});
	}

	protected void postFailure() {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				Requester.this.listener.failure();
			}
		});
	}

	protected abstract T executeRequest(Socket socket) throws IOException;
}
