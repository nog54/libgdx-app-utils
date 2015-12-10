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

package org.nognog.gdx.jmatcher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.nognog.gdx.jmatcher.requester.CancelEntryListener;
import org.nognog.gdx.jmatcher.requester.FindEntryListener;
import org.nognog.gdx.jmatcher.requester.MakeEntryListener;
import org.nognog.gdx.jmatcher.requester.Requester;
import org.nognog.jmatcher.JMatcher;
import org.nognog.jmatcher.JMatcherClientUtils;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

/**
 * @author goshi 2015/11/27
 */
public class GdxJMatcherClient {

	private final ExecutorService service;

	private String host;
	private int port;
	private SocketHints hints;
	private int retryCount;

	private static final int defalutRetryCount = 2;

	/**
	 * @param host
	 */
	public GdxJMatcherClient(String host) {
		this(host, JMatcher.PORT);
	}

	/**
	 * @param host
	 * @param port
	 */
	public GdxJMatcherClient(String host, int port) {
		this(host, port, new SocketHints());
	}

	/**
	 * @param host
	 * @param hints
	 */
	public GdxJMatcherClient(String host, SocketHints hints) {
		this(host, JMatcher.PORT, hints);
	}

	/**
	 * @param host
	 * @param port
	 * @param hints
	 */
	public GdxJMatcherClient(String host, int port, SocketHints hints) {
		this.host = host;
		this.port = port;
		this.hints = hints;
		this.service = Executors.newSingleThreadExecutor();
		this.retryCount = defalutRetryCount;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the hints
	 */
	public SocketHints getHints() {
		return this.hints;
	}

	/**
	 * @param hints
	 *            the hints to set
	 */
	public void setHints(SocketHints hints) {
		this.hints = hints;
	}

	/**
	 * @return the retryCount
	 */
	public int getRetryCount() {
		return this.retryCount;
	}

	/**
	 * @param retryCount
	 *            the retryCount to set
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * @param listener
	 *
	 */
	public void makeEntry(final MakeEntryListener listener) {
		this.service.execute(new Requester<Integer>(this.host, this.port, this.hints, this.retryCount, listener) {
			@Override
			protected Integer executeRequest(final Socket socket) throws IOException {
				try (final ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
					return JMatcherClientUtils.makeEntry(oos, ois);
				}
			}
		});
	}

	/**
	 * @param key
	 * @param listener
	 */
	public void cancelEntry(final Integer key, final CancelEntryListener listener) {
		this.service.execute(new Requester<Boolean>(this.host, this.port, this.hints, this.retryCount, listener) {
			@Override
			protected Boolean executeRequest(final Socket socket) throws IOException {
				try (final ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
					return Boolean.valueOf(JMatcherClientUtils.cancelEntry(key, oos, ois));
				}
			}
		});
	}

	/**
	 * @param key
	 * @param listener
	 */
	public void findEntry(final Integer key, final FindEntryListener listener) {
		this.service.execute(new Requester<InetAddress>(this.host, this.port, this.hints, this.retryCount, listener) {
			@Override
			protected InetAddress executeRequest(final Socket socket) throws IOException {
				try (final ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
					return JMatcherClientUtils.findEntry(key, oos, ois);
				}
			}
		});
	}
}