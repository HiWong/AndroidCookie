package com.taohu.androidcookie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Communicator controls the communication between the client and the server
 */
public class Communicator {

	public Communicator() {

	}

	public String httpGet(String url) throws InterruptedException, ExecutionException {
		return new HttpGetTask(url).execute().get();
	}

	protected class HttpGetTask extends AsyncTask<Void, Void, String> {
		private String url;

		public HttpGetTask(String url) {
			this.url = url;
		}

		@Override
		protected String doInBackground(Void... voids) {
			String str = "";
			try {
				// Log.d("Communicator", "enter HttpGet ");
				CookieStore cookieStore = new BasicCookieStore();
				Cookie cookie = new BasicClientCookie("forms9slides", "9162BD577880CE411E4F875C5A6AD624D1C4399AC821D0FD048D6CA2D152BB8A0C9322E071A9A7EF814CB514657C54151075E8B363010B86E41818CA67F29FEF63C1CFBCCE7AC5A445234974350D532830E1DF6F37B07B43FD5905CBFF7C44633B0D3707F8545731BC31EAC0357F5791A8D563DFFD87DBE29E0BC08C6A340FB2F65DEECB48768D14D89B43BA6FC563DD0D77C955629DCFA407E5DEE2EB5199E17EBB35A353ACDC79D844414F892C4AB1");
				cookieStore.addCookie(cookie);
				HttpContext localContext = new BasicHttpContext();
				localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

				DefaultHttpClient hc = new DefaultHttpClient();
				HttpGet hg = new HttpGet(url);
				HttpResponse hr = hc.execute(hg, localContext);

				InputStream in = null;

				if (hr != null) {
					HttpEntity he = hr.getEntity();
					in = he.getContent();
				}
				// read the input stream
				if (in != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					if (reader != null) {
						while ((line = reader.readLine()) != null)
							sb.append(line + "\n");
					}
					in.close();
					str = sb.toString();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	public Bitmap requestImage(String url) throws InterruptedException, ExecutionException {
		return new HttpGetImageTask(url).execute().get();
	}

	protected class HttpGetImageTask extends AsyncTask<Void, Void, Bitmap> {
		private String imgUrl;

		public HttpGetImageTask(String url) {
			imgUrl = url;
		}

		@Override
		protected Bitmap doInBackground(Void... voids) {
			Bitmap img = null;
			try {
				img = BitmapFactory.decodeStream((InputStream) new URL(imgUrl).getContent());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				return img;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return img;
		}
	}
}

// tao123
// http://bitium.192.168.1.44.xip.io/api/v1/2/subscriptions?token=xx-WGhLQx_QFFX_E5rpb
// https://www.bitium.com/api/v1/1/subscriptions?token=BWJnpS5LoRxZ9GvShshM

