package com.supcon.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.EditText;

import com.app.annotation.apt.Router;
import com.supcon.common.com_router.event.OkBus;
import com.supcon.common.com_router.util.RouterManager;
import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.view.loader.CircularLoaderView;



@Router("main")
public class MainActivity extends BaseActivity {

    CircularLoaderView mCircularLoaderView;

    RouterManager mRouterManager = RouterManager.getInstance();

    String result;

    EditText mText;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInit() {
        super.onInit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.routerText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentRouter.go(context, "routerTest");
            }
        });
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onLoading("正在加载1123");
                onLoadFailed("2343");
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            onLoadFailed("2343");
//                        }
//                    }, 5000);
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoading("正在加载...");
                onLoadSuccess();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                            onLoadSuccess();
//                    }
//                }, 5000);
            }
        });

        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoading("正在加载...");

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        onLoading("正在解析2000");
//                    }
//                }, 2000);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        onLoading("正在解析4000");
//                    }
//                }, 4000);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        onLoading("正在解析6000");
//                    }
//                }, 6000);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        onLoading("正在解析8000");
//                    }
//                }, 8000);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        onLoadSuccess("解析成功！");
//                    }
//                }, 10000);
                onLoading("正在解析2000");
                onLoading("正在解析4000");
                onLoading("正在解析6000");
                onLoading("正在解析8000");
                onLoading("正在解析10000");
                onLoading("正在解析12000");
                onLoadSuccess("解析成功！");
            }
        });

        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoading("正在加载...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            loaderController.closeLoader();
                    }
                }, 5000);
            }
        });

        mCircularLoaderView = findViewById(R.id.btn2);

        mCircularLoaderView.setDoneColor(ContextCompat.getColor(context, R.color.bapThemeBlue));
        mCircularLoaderView.setInitialHeight(100);
        mCircularLoaderView.setSpinningBarColor(ContextCompat.getColor(context, R.color.bapThemeOrange));

        mCircularLoaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButtonAndRevert((CircularLoaderView) v, ContextCompat.getColor(context, R.color.bapThemeBlue),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_success2));
            }
        });

        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkBus.getInstance().onEvent(1);
            }
        });


        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CookieManager.getInstance().removeAllCookies(null);
//                CookieManager.getInstance().flush();
                Bundle bundle = new Bundle();
                bundle.putString(BaseConstant.WEB_COOKIE, "JSESSIONID=39FFC78302C4C6477DAED3839E4CDC3A; CASTGC=TGT-6-1ZvxxSS9zFVUrbJ2qxfehwAqluDoMVPxdbnNJCmyVSydMvyOZk");
                bundle.putString(BaseConstant.WEB_AUTHORIZATION, "CASTGC TGT-6-1ZvxxSS9zFVUrbJ2qxfehwAqluDoMVPxdbnNJCmyVSydMvyOZk");
                bundle.putString(BaseConstant.WEB_URL,"http://192.168.90.41:8080/BEAMEle/onOrOff/onoroff/eleOnList.action?__pc__=QkVBTUVsZV8xLjAuMF9vbk9yT2ZmX2VsZU9uTGlzdF9zZWxmfA__&workFlowMenuCode=BEAMEle_1.0.0_onOrOff_eleOffList&openType=page&clientType=mobile&date=1547015106860");
                bundle.putBoolean(BaseConstant.WEB_HAS_REFRESH, true);
                bundle.putBoolean(BaseConstant.WEB_IS_LIST, true);
                IntentRouter.go(context, "webview", bundle);
            }
        });

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

//        onLoading("正在加载。。。");
        OkBus.getInstance().onEvent(1);
    }

    private void animateButtonAndRevert(final CircularLoaderView circular, final int fillColor, final Bitmap bitmap) {
        Handler handler = new  Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                circular.doneLoadingAnimation(fillColor, bitmap);
            }
        };

        Runnable runnableRevert = new Runnable() {
            @Override
            public void run() {
                circular.revertAnimation();
                circular.setSpinningBarColor(Color.MAGENTA);
            }
        };


        circular.revertAnimation();

        circular.startAnimation();
        handler.postDelayed(runnable, 3000);
        handler.postDelayed(runnableRevert, 4000);
        handler.postDelayed(runnableRevert, 4100);
    }


    public void onLogin() {
        LogUtil.i("onLogin");
        LogUtil.i("onLogin");
    }
}
