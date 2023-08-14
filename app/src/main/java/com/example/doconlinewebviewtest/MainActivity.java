package com.example.doconlinewebviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
public class MainActivity extends Activity {

    private final static int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri[]> mUploadMessage;
    private PDFView pdfView;
    WebView mainWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        pdfView = findViewById(R.id.pdfView);
        mainWebView = findViewById(R.id.webView);

        mainWebView.setWebViewClient(new MyWebViewClient());

        mainWebView.setWebChromeClient(new MyWebChromeClient());
        mainWebView.getSettings().setJavaScriptEnabled(true);
        mainWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mainWebView.getSettings().setAllowFileAccess(true);
        mainWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mainWebView.getSettings().setSupportMultipleWindows(true);

        mainWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        mainWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mainWebView.getSettings().setDomStorageEnabled(true);
        mainWebView.getSettings().setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mainWebView.getSettings().setSafeBrowsingEnabled(true);
        }
        mainWebView.loadUrl("https://demo.doconline.com");

//        settings.domStorageEnabled = true
//        settings.allowContentAccess = true
//        settings.safeBrowsingEnabled = true
//        settings.mediaPlaybackRequiresUserGesture = false

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == FILECHOOSER_RESULTCODE) {

            if (null == mUploadMessage || intent == null || resultCode != RESULT_OK) {
                return;
            }

            Uri[] result = null;
            String dataString = intent.getDataString();

            if (dataString != null) {
                result = new Uri[]{ Uri.parse(dataString) };
            }

            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }


    // ====================
    // Web clients classes
    // ====================

    /**
     * Clase para configurar el webview
     */
    private class MyWebViewClient extends WebViewClient {


//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//
//            if (url.endsWith(".pdf")) {
//                // Load PDF.js and the PDF file using JavaScript
//                mainWebView.loadUrl("javascript:(function() { " +
//                        "var viewer = document.querySelector('#viewer');" +
//                        "PDFJS.getDocument('" + url + "').promise.then(function(pdf) {" +
//                        "  pdf.getPage(1).then(function(page) {" +
//                        "    var canvas = document.createElement('canvas');" +
//                        "    var scale = 1.5;" +
//                        "    var viewport = page.getViewport({scale: scale});" +
//                        "    var context = canvas.getContext('2d');" +
//                        "    canvas.height = viewport.height;" +
//                        "    canvas.width = viewport.width;" +
//                        "    viewer.appendChild(canvas);" +
//                        "    page.render({canvasContext: context, viewport: viewport});" +
//                        "  });" +
//                        "});" +
//                        "})();");
//            }
//        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            String path = uri.getPath();
            String query = uri.getQuery();
//            if (url.endsWith(".pdf") || url.endsWith(".xls") || url.endsWith(".xlsx")) {
//                // Handle the URL to load within the WebView
//                view.loadUrl( url);
//                return true; // Indicates that the URL has been handled
//            } else if (query != null && query.contains("Policy") && query.contains("Signature")) {
//                // Handle CloudFront signed URLs here, such as displaying a message or redirecting
//                // to a different page, as necessary.
//                return true;
//            }else {
//                // Load other URLs as usual
//                return super.shouldOverrideUrlLoading(view, url);
//            }
            if (path != null && (path.endsWith(".pdf") || path.endsWith(".xls") || path.endsWith(".xlsx"))) {
                // Assuming 'view' is the WebView instance
                view.loadUrl(url);
                return true;
            } else if (query != null && query.contains("Policy") && query.contains("Signature")) {
                // Handle CloudFront signed URLs here, such as displaying a message or redirecting
                // to a different page, as necessary.
                // Load the PDF using the URL
//                pdfView.fromUri(Uri.parse(url))
//                        .onLoad(new OnLoadCompleteListener() {
//                            @Override
//                            public void loadComplete(int nbPages) {
//                                // PDF has been loaded, you can implement any necessary logic here
//                            }
//                        })
//                        .scrollHandle(new DefaultScrollHandle(MainActivity.this))
//                        .load();

//                view.loadUrl(url);
                return true;
            } else {
                // Handle other URLs or conditions, if needed
                return false;
            }






        }

        // permite la navegacion dentro del webview
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
    }


    /**
     * Clase para configurar el chrome client para que nos permita seleccionar archivos
     */
    private class MyWebChromeClient extends WebChromeClient {
//        @Override
//        public void onPermissionRequest(PermissionRequest request){
//            String[] resources = request.getResources();
//
//            switch (resources[0]){
//                case PermissionRequest.RESOURCE_AUDIO_CAPTURE:
//                    Toast.makeText(getBaseContext(), "Audio Permission", Toast.LENGTH_SHORT).show();
//                    request.grant(new String[]{PermissionRequest.RESOURCE_AUDIO_CAPTURE});
//                    break;
//                case PermissionRequest.RESOURCE_MIDI_SYSEX:
//                    Toast.makeText(getBaseContext(), "MIDI Permission", Toast.LENGTH_SHORT).show();
//                    request.grant(new String[]{PermissionRequest.RESOURCE_MIDI_SYSEX});
//                    break;
//                case PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID:
//                    Toast.makeText(getBaseContext(), "Encrypted media permission", Toast.LENGTH_SHORT).show();
//                    request.grant(new String[]{PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID});
//                    break;
//                case PermissionRequest.RESOURCE_VIDEO_CAPTURE:
//                    Toast.makeText(getBaseContext(), "Video Permission", Toast.LENGTH_SHORT).show();
//                    request.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
//                    break;
//            }
//        }
@Override
public void onPermissionRequest(PermissionRequest request) {
    runOnUiThread(() -> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String[] PERMISSIONS = {
                    PermissionRequest.RESOURCE_AUDIO_CAPTURE,
                    PermissionRequest.RESOURCE_VIDEO_CAPTURE
            };
            request.grant(PERMISSIONS);
        }
    });
}
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {

//            WebView.HitTestResult result = view.getHitTestResult();
//            String data = result.getExtra();
//            Context context = view.getContext();
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
//            context.startActivity(browserIntent);
//            return false;
            WebView newWebView = new WebView(MainActivity.this);
            view.addView(newWebView);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(newWebView);
            resultMsg.sendToTarget();

            newWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(url));
                    startActivity(browserIntent);
                    return true;
                }
            });
            return true;
        }

        // maneja la accion de seleccionar archivos
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

            // asegurar que no existan callbacks
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }

            mUploadMessage = filePathCallback;

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*"); // set MIME type to filter

            MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), MainActivity.FILECHOOSER_RESULTCODE );

            return true;
        }
    }

}
//import android.os.Bundle;
//
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.net.http.SslError;
//import android.os.Bundle;
//import android.webkit.SslErrorHandler;
//import android.webkit.ValueCallback;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//
//public class MainActivity extends AppCompatActivity {
//    private WebView webView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        WebView webView = (WebView)findViewById(R.id.webView);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("https://www.google.com");
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//    }
//    public void onBackPressed(){
//        if (webView.canGoBack()){
//            webView.goBack();
//        } else {
//            super.onBackPressed();
//        }
//    }
//}
//
//
////public class MainActivity extends AppCompatActivity {
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////    }
////}