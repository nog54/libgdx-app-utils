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

import org.nognog.jmatcher.JMatcherClientUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

/**
 * @author goshi 2015/11/27
 */
public class GdxJMatcherClient {

	private String host;
	private int port;
	private SocketHints hints;
	private int retryCount;

	private static final int defaultPort = 11600;
	private static final int defalutRetryCount = 2;

	/**
	 * @param host
	 */
	public GdxJMatcherClient(String host) {
		this(host, defaultPort);
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
		this(host, defaultPort, hints);
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
	 * @return entry key number, or null is returned if failed to get entry key
	 * @throws IOException
	 *             It's thrown if failed to connect to the server
	 */
	public Integer makeEntry() throws IOException {
		final Socket socket = Gdx.net.newClientSocket(Protocol.TCP, this.host, this.port, this.hints);
		for (int i = 0; i < this.retryCount; i++) {
			try (final ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
				final Integer entryKey = JMatcherClientUtils.makeEntry(oos, ois);
				socket.dispose();
				return entryKey;
			} catch (IOException e) {
				// failed
			}
		}
		socket.dispose();
		throw new IOException("failed to connect to the server"); //$NON-NLS-1$
	}

	/**
	 * @param key
	 * @return true if success
	 * @throws IOException
	 *             It's thrown if failed to connect to the server
	 */
	public boolean cancelEntry(Integer key) throws IOException {
		final Socket socket = Gdx.net.newClientSocket(Protocol.TCP, this.host, this.port, this.hints);
		for (int i = 0; i < this.retryCount; i++) {
			try (final ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
				final boolean isSuccess = JMatcherClientUtils.cancelEntry(key, oos, ois);
				socket.dispose();
				return isSuccess;
			} catch (IOException e) {
				// failed
			}
		}
		socket.dispose();
		throw new IOException("failed to connect to the server"); //$NON-NLS-1$
	}

	/**
	 * @param key
	 * @return response
	 * @throws IOException
	 *             It's thrown if failed to connect to the server
	 */
	public InetAddress findEntry(Integer key) throws IOException {
		final Socket socket = Gdx.net.newClientSocket(Protocol.TCP, this.host, this.port, this.hints);
		for (int i = 0; i < this.retryCount; i++) {
			try (final ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
				final InetAddress address = JMatcherClientUtils.findEntry(key, oos, ois);
				socket.dispose();
				return address;
			} catch (IOException e) {
				// failed
			}
		}
		socket.dispose();
		throw new IOException("failed to connect to the server"); //$NON-NLS-1$
	}
}