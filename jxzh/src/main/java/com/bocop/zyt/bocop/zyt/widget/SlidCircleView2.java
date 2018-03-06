package com.bocop.zyt.bocop.zyt.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bocop.zyt.fmodule.utils.DisplayUtil;

/**
 * Created by ltao on 2017/3/16.
 */

public class SlidCircleView2 extends ImageView {

    private float x_touch;
    private float y_touch;
    private int _width;
    private int _height;
    private int padding;//px
    private Paint paint_point;
    private boolean ontouch;
    private int first_area;
    private int front_area;
    private float x_touch_first;
    private float y_touch_first;
    private boolean down_press;
    private boolean up_press;

    private static final int SideInstance = 180;//靠近边缘生效

    public static void log(String s) {
        Log.e("SlidCircleView", s);
    }

    public SlidCircleView2(Context context) {
        super(context);
        init();
    }

    public SlidCircleView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidCircleView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //    public SlidCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
    private int ciccle_pading = 18;
    private Callback _cb;

    private void init() {
        padding = DisplayUtil.dip2px(getContext(), ciccle_pading);
        this.setPadding(padding, padding, padding, padding);
        onTouch();
        paint_point = new Paint();
        paint_point.setAntiAlias(true);
        paint_point.setColor(Color.BLACK);
        paint_point.setAlpha(180);
//        paint_point.setStrokeWidth(4);
//        paint_point.setStyle(Paint.Style.STROKE);
        paint_point.setStyle(Paint.Style.FILL);

    }

    private void onTouch() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x_touch = event.getX();
                y_touch = event.getY();
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL: {
//                        log("ACTION_CANCEL");
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
//                        log("ACTION_DOWN");
                        first_area = touch_area();
                        down_press = false;
                        up_press = false;
                        if (first_area == 1 || first_area == 3 || first_area == 0) {
//                            return true;
                            ontouch = false;
                        } else {

                            x_touch_first = event.getX();
                            y_touch_first = event.getY();
                            ontouch = true;
                        }
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
//                        log("ACTION_MOVE");

                        int now_area = touch_area();

                        if((front_area == 2 && now_area == 1)){

                            first_area=1;
                        }else if((front_area == 4 && now_area == 3)){
                            first_area=3;
                        }


                        log("front_area " + front_area + " now_area " + now_area);
                        if ((first_area == 2 && now_area == 1) || (first_area == 4 && now_area == 3) || (first_area == 1 && now_area == 1) || (first_area == 3 && now_area == 3)) {
//                            return true;
                            ontouch = false;
                        } else {
                            ontouch = true;
                        }


                        if(front_area!=now_area){
                            front_area=now_area;
                        }

                        break;
                    }
                    case MotionEvent.ACTION_UP: {
//                        log("ACTION_UP");
                        ontouch = false;


                        if (down_press) {
                        	if(_cb!=null){
                        		_cb.down();
                        	}
                        } else if (up_press) {
                        	if(_cb!=null){
                        		_cb.up();
                        	}

                        } else {

                        }

                        break;
                    }
                }
                invalidate();
                return true;
            }
        });
    }

    public void setCallback(Callback cb) {
        this._cb = cb;
    }

    public interface Callback {
        public void up();

        public void down();
    }


    private int touch_area() {
        int r_big = (_width / 2 - padding);

        int r = r_big / 6;

        int center_x = _width / 2;
        int center_y = _height / 2;

        float abs_x = Math.abs(x_touch - center_x);
        float abs_y = Math.abs(y_touch - center_y);
//        if ((abs_x * abs_x + abs_y * abs_y) >= (r_big - r) * (r_big - r)) {
//
//            return;
//        }
//        int r_big = (_width / 2 - padding);

//        int r = r_big / 6;
        int r_path = r_big - r / 2;
        float r_small = r_path / 2;

        float small_x_center=center_x;
        float small_y_center ;
        int area = 0;


        float x_touch_real = x_touch - center_x;
        float y_touch_real = -y_touch + center_y;

        if (x_touch_real > 0 && y_touch_real > 0) {
            area = 1;
            small_x_center = center_x + r_small;

//            if ((x_touch - small_x_center) * (x_touch - small_x_center) + (y_touch - small_y_center) * (y_touch - small_y_center) < r_small * r_small) {
//                area = 4;
//            }


        } else if (x_touch_real < 0 && y_touch_real > 0) {

            small_y_center = center_x - r_small;

            if ((x_touch - small_x_center) * (x_touch - small_x_center) + (y_touch - small_y_center) * (y_touch - small_y_center) < r_small * r_small) {
                area = 1;
            }

            area = 2;
        } else if (x_touch_real < 0 && y_touch_real < 0) {
            area = 3;
            small_x_center = center_x - r_small;

//            if ((x_touch - small_x_center) * (x_touch - small_x_center) + (y_touch - small_y_center) * (y_touch - small_y_center) < r_small * r_small) {
//                area = 2;
//            }


        } else if (x_touch_real > 0 && y_touch_real < 0) {
            area = 4;
            small_y_center = center_y + r_small;

            if ((x_touch - small_x_center) * (x_touch - small_x_center) + (y_touch - small_y_center) * (y_touch - small_y_center) < r_small * r_small) {
                area = 3;
            }
        }

        return area;
    }

//    private void draw_point(Canvas canvas){
//
//
//    }

    private void draw_point(Canvas canvas) {


        int r_big = (_width / 2 - padding);

        int r = r_big / 6;
        int r_path = r_big - r / 2;
        float r_small = r_path / 2;
        int center_x = _width / 2;
        int center_y = _height / 2;

        float abs_x = Math.abs(x_touch - center_x);
        float abs_y = Math.abs(y_touch - center_y);
//        if ((abs_x * abs_x + abs_y * abs_y) >= (r_big - r) * (r_big - r)) {
//
//            return;
//        }


        int area = touch_area();

//
//        float x_touch_real = x_touch - center_x;
//        float y_touch_real = -y_touch + center_y;
//
//
//        float line_leng = x_touch_real * x_touch_real + y_touch_real * y_touch_real;
//
//        if (x_touch_real > 0 && y_touch_real > 0) {
//            area = 1;
////            if (line_leng < r_small * r_small) {
////                area = 4;
////            }
//        } else if (x_touch_real < 0 && y_touch_real > 0) {
//            area = 2;
//        } else if (x_touch_real < 0 && y_touch_real < 0) {
//            area = 3;
////            if (line_leng < r_small * r_small) {
////                area = 2;
////            }
//        } else if (x_touch_real > 0 && y_touch_real < 0) {
//            area = 4;
//        }


        float x_touch_rl = Math.abs(x_touch - center_x);
        float y_touch_rl = Math.abs(y_touch - center_y);


        int r_c = r_path;

        float c_x_rl = (float) Math.sqrt((x_touch_rl * x_touch_rl * r_c * r_c) / (x_touch_rl * x_touch_rl + y_touch_rl * y_touch_rl));
        float c_y_rl = (float) Math.sqrt((y_touch_rl * y_touch_rl * r_c * r_c) / (x_touch_rl * x_touch_rl + y_touch_rl * y_touch_rl));

//        float c_x_rl=x_touch_rl;
//        float c_y_rl=y_touch_rl;
        float c_x_real = 0;
        float c_y_real = 0;


        switch (area) {
            case 1: {
                c_x_real = center_x + c_x_rl;
                c_y_real = center_x - c_y_rl;
                break;
            }
            case 2: {
                c_x_real = center_x - c_x_rl;
                c_y_real = center_x - c_y_rl;

                break;
            }
            case 3: {
                c_x_real = center_x - c_x_rl;
                c_y_real = center_x + c_y_rl;
                break;
            }
            case 4: {
                c_x_real = center_x + c_x_rl;
                c_y_real = center_x + c_y_rl;
                break;
            }
        }

        Path path = new Path();

        path.setFillType(Path.FillType.WINDING);


        RectF rect = new RectF(center_x - r_path, center_y - r_path, center_x + r_path, center_y + r_path);


        float angle = 0;


        float x_x1_real = 0;
        float y_y1_real = 0;


        float x_x1_rl = 0;
        float y_y1_rl = 0;

        up_press = false;
        down_press = false;

        int arc_rate = 36;
        switch (area) {


            case 1: {

                angle = (float) (Math.toDegrees(Math.atan(c_y_rl / c_x_rl)));

                y_y1_rl = Math.abs(c_y_rl - r_small);
                x_x1_rl = (float) Math.sqrt(r_small * r_small - y_y1_rl * y_y1_rl);
                x_x1_real = center_x - x_x1_rl;
                y_y1_real = (float) (center_y - c_y_rl);

                path.addArc(rect, 90, -(angle + 90));


//                path.lineTo(x_x1_real, y_y1_real);?

//                path.lineTo(center_x, center_y + r_path);

//                log("c_x_rl" + x_x1_rl + "  " + r_path);
                if (c_y_rl + arc_rate * 2.0 / 3 >= r_path) {

//                    path.lineTo(x_x1_real, y_y1_real);
                    arc_rate = (int) ((r_path - c_y_rl) * 3.0 / 2);
                }

//                else {

                path.cubicTo(
                        (float) (x_x1_real + ((c_x_rl + x_x1_rl) * (2.0 / 3))), (float) (y_y1_real - arc_rate),
                        (float) (x_x1_real + ((c_x_rl + x_x1_rl) * (1.0 / 3))), (float) (y_y1_real - arc_rate),
                        x_x1_real, y_y1_real);
//                }
//
//
                path.lineTo(center_x, center_y);
//
                RectF rectF = new RectF(center_x - r_small, center_y - r_path, center_x + r_small, center_y);
                float angle_left = (float) Math.toDegrees(Math.atan(x_x1_rl / y_y1_rl));
                if (c_y_rl - r_small >= 0) {
                    path.addArc(rectF, -(90 + angle_left), angle_left - 180);
                } else {
                    path.addArc(rectF, angle_left - 270, -angle_left);
                }
                path.lineTo(center_x, center_y);


//                path.lineTo(center_x, center_y - r_path);
                RectF rectF1 = new RectF(center_x - r_small, center_y, center_x + r_small, center_y + r_path);
                path.addArc(rectF1, -90, 180);
//
////                path.cubicTo(center_x, center_y, center_x + r_small/2, center_y - r_small,center_x + r_small,center_y - r_small);
//                path.lineTo(center_x + r_path, center_y);
//
                path.lineTo(center_x, center_y + r_path);
//                if (c_x_rl >= r_path - SideInstance) {
//                    down_press = false;
//                    up_press = true;
//
//                } else {
//                    up_press = false;
//                    down_press = false;
//                }


//                angle = (float) (-Math.toDegrees(Math.atan(c_y_rl / c_x_rl)));
//
//                x_x1_rl = Math.abs(c_x_rl - r_small);
//                y_y1_rl = (float) Math.sqrt(r_small * r_small - (r_small - c_x_rl) * (r_small - c_x_rl));
//
//
////                if(x_x1_rl*x_x1_rl+y_y1_rl*y_y1_rl<=r_small*r_small){
////                    return;
////                }
//
//                x_x1_real = c_x_rl + center_x;
//                y_y1_real = (float) (center_y - y_y1_rl);
//
//                path.addArc(rect, 0, angle);
////
////                path.lineTo(x_x1_real, y_y1_real);
////                float r_arc=(c_y_rl-y_y1_rl)/2;
////                path.addArc(new RectF(center_x+c_x_rl-r_arc,center_y-(c_y_rl-r_arc),center_x+c_x_rl+r_arc,center_y-(c_y_rl-2*r_arc)),-90,180);
////                path.lineTo(x_x1_real, y_y1_real);
//
//
//                if (c_x_rl + arc_rate * 6.0 / 3 > r_path) {
////                    path.lineTo(x_x1_real, y_y1_real);
//                    arc_rate = (int) ((r_path - c_x_rl) * 1.0 / 2);
//                }
//
//                path.cubicTo(
//                        (float) (x_x1_real - arc_rate), (float) (center_y - y_y1_rl - ((c_y_rl - y_y1_rl) * (2.0 / 3))),
//                        (float) (x_x1_real - arc_rate), (float) (center_y - y_y1_rl - ((c_y_rl - y_y1_rl) * (1.0 / 3))),
//                        x_x1_real, y_y1_real);
//
//                RectF rectF = new RectF(center_x, center_y - r_small, center_x + r_path, center_y + r_small);
//                if (c_x_rl - r_small >= 0) {
//                    path.addArc(rectF, (float) (-Math.toDegrees(Math.atan(y_y1_rl / x_x1_rl))), (float) (Math.toDegrees(Math.atan(y_y1_rl / x_x1_rl))));
//                } else {
//                    path.addArc(rectF, (float) (-180 + Math.toDegrees(Math.atan(y_y1_rl / x_x1_rl))), (float) (180 - Math.toDegrees(Math.atan(y_y1_rl / x_x1_rl))));
//                }


//                path.lineTo(center_x + r_path, center_y);


                if (c_y_rl >= r_path - SideInstance) {
                    down_press = false;
                    up_press = true;

                } else {
                    up_press = false;
                    down_press = false;
                }

                break;
            }
            case 2: {


                angle = (float) (Math.toDegrees(Math.atan(c_x_rl / c_y_rl)));

                y_y1_rl = Math.abs(c_y_rl - r_small);
                x_x1_rl = (float) Math.sqrt(r_small * r_small - y_y1_rl * y_y1_rl);
                x_x1_real = center_x - x_x1_rl;
                y_y1_real = (float) (center_y - c_y_rl);

                path.addArc(rect, -90, -angle);
//
                if (c_y_rl + arc_rate * 6.0 / 3 > r_path) {
//                    path.lineTo(x_x1_real, y_y1_real);
                    arc_rate = (int) ((r_path - c_y_rl) * 1.0 / 2);
                }
                path.cubicTo(
                        (float) (x_x1_real - ((c_x_rl - x_x1_rl) * (2.0 / 3))), (float) (y_y1_real + arc_rate),
                        (float) (x_x1_real - ((c_x_rl - x_x1_rl) * (1.0 / 3))), (float) (y_y1_real + arc_rate),
                        x_x1_real, y_y1_real);
//
//                path.lineTo(x_x1_real, y_y1_real);
//、
//
//                path.lineTo(center_x, center_y);
                float abs_angle = (float) (Math.toDegrees(Math.atan(x_x1_rl / y_y1_rl)));
                RectF rectF = new RectF(center_x - r_small, center_y - r_path, center_x + r_small, center_y);
                if (c_y_rl - r_small >= 0) {
                    path.addArc(rectF, -90 - abs_angle, abs_angle);
                } else {
                    path.addArc(rectF, -270 + abs_angle, 180 - abs_angle);
                }


                path.lineTo(center_x, center_y - r_path);


//                angle = (float) (-180 + Math.toDegrees(Math.atan(c_y_rl / c_x_rl)));
//
//                x_x1_rl = Math.abs(c_x_rl - r_small);
//                y_y1_rl = (float) Math.sqrt(r_small * r_small - (r_small - c_x_rl) * (r_small - c_x_rl));
//                x_x1_real = -c_x_rl + center_x;
//                y_y1_real = (float) (center_y + y_y1_rl);
//
//                path.addArc(rect, 0, angle);
//
//
////                log("c_x_rl" + x_x1_rl + "  " + r_path);
//                if (c_x_rl + arc_rate * 2.0 / 3 >= r_path) {
//
////                    path.lineTo(x_x1_real, y_y1_real);
//                    arc_rate = (int) ((r_path - c_x_rl) * 3.0 / 2);
//                }
//
////                else {
//
//                path.cubicTo(
//                        (float) (x_x1_real - arc_rate), (float) (y_y1_real - ((c_y_rl + y_y1_rl) * (2.0 / 3))),
//                        (float) (x_x1_real - arc_rate), (float) (y_y1_real - ((c_y_rl + y_y1_rl) * (1.0 / 3))),
//                        x_x1_real, y_y1_real);
////                }
//
//
//                path.lineTo(center_x, center_y);
//
//                RectF rectF = new RectF(center_x - r_path, center_y - r_small, center_x, center_y + r_small);
//                if (c_x_rl - r_small <= 0) {
//                    path.addArc(rectF, (float) (Math.toDegrees(Math.atan(y_y1_rl / x_x1_rl))), -(float) (Math.toDegrees(Math.atan(y_y1_rl / x_x1_rl))));
//                } else {
//                    path.addArc(rectF, (float) (180 - Math.toDegrees(Math.atan(y_y1_rl / x_x1_rl))), -180 + (float) (Math.toDegrees(Math.atan(y_y1_rl / x_x1_rl))));
//                }
//                path.lineTo(center_x, center_y);
//                RectF rectF1 = new RectF(center_x, center_y - r_small, center_x + r_path, center_y + r_small);
//                path.addArc(rectF1, 180, 180);
//
////                path.cubicTo(center_x, center_y, center_x + r_small/2, center_y - r_small,center_x + r_small,center_y - r_small);
//                path.lineTo(center_x + r_path, center_y);
//
//                if (c_x_rl >= r_path - SideInstance) {
//                    down_press = false;
//                    up_press = true;
//
//                } else {
//                    up_press = false;
//                    down_press = false;
//                }
                break;
            }
            case 3: {


                angle = (float) (Math.toDegrees(Math.atan(c_x_rl / c_y_rl)));

                y_y1_rl = Math.abs(c_y_rl - r_small);
                x_x1_rl = (float) Math.sqrt(r_small * r_small - y_y1_rl * y_y1_rl);
                x_x1_real = center_x + x_x1_rl;
                y_y1_real = (float) (center_y + c_y_rl);

                path.addArc(rect, -90, angle - 180);


//                path.lineTo(x_x1_real, y_y1_real);?

//                path.lineTo(center_x, center_y + r_path);

//                log("c_x_rl" + x_x1_rl + "  " + r_path);
                if (c_y_rl + arc_rate * 2.0 / 3 >= r_path) {

//                    path.lineTo(x_x1_real, y_y1_real);
                    arc_rate = (int) ((r_path - c_y_rl) * 3.0 / 2);
                }

//                else {

                path.cubicTo(
                        (float) (x_x1_real - ((c_x_rl + x_x1_rl) * (2.0 / 3))), (float) (y_y1_real + arc_rate),
                        (float) (x_x1_real - ((c_x_rl + x_x1_rl) * (1.0 / 3))), (float) (y_y1_real + arc_rate),
                        x_x1_real, y_y1_real);
//                }
////

//                path.lineTo(x_x1_real, y_y1_real);
                path.lineTo(center_x, center_y);
//
                RectF rectF = new RectF(center_x - r_small, center_y, center_x + r_small, center_y + r_path);
                float angle_left = (float) Math.toDegrees(Math.atan(x_x1_rl / y_y1_rl));
                if (c_y_rl - r_small >= 0) {
                    path.addArc(rectF, -270 - angle_left, -180 + angle_left);
                } else {
                    path.addArc(rectF, -90 + angle_left, -angle_left);
                }
                path.lineTo(center_x, center_y);

//
                RectF rectF1 = new RectF(center_x - r_small, center_y - r_path, center_x + r_small, center_y);
                path.addArc(rectF1, 90, 180);

                path.lineTo(center_x, center_y - r_path);



                if (c_y_rl >= r_path - SideInstance) {
                    down_press = true;
                    up_press = false;

                } else {
                    up_press = false;
                    down_press = false;
                }


//                angle = (float) (Math.toDegrees(Math.atan(c_y_rl / c_x_rl)));
//
//                x_x1_rl = Math.abs(c_x_rl - r_small);
//                y_y1_rl = (float) Math.sqrt(r_small * r_small - (r_small - c_x_rl) * (r_small - c_x_rl));
//
////                if(x_x1_rl*x_x1_rl+y_y1_rl*y_y1_rl<=r_small*r_small){
////                    return;
////                }
//
//
//                x_x1_real = -c_x_rl + center_x;
//                y_y1_real = (float) (center_y + y_y1_rl);
//
//                path.addArc(rect, -180, -angle);
////
////                path.lineTo(x_x1_real, y_y1_real);
//
//
//                if (c_x_rl + arc_rate * 6.0 / 3 > r_path) {
//
////                    path.lineTo(x_x1_real, y_y1_real);
//
//                    arc_rate = (int) ((r_path - c_x_rl) * 1.0 / 2);
//                }
////                else{
//
//                path.cubicTo(
//                        (float) (x_x1_real + arc_rate), (float) (center_y + y_y1_rl + ((c_y_rl - y_y1_rl) * (2.0 / 3))),
//                        (float) (x_x1_real + arc_rate), (float) (center_y + y_y1_rl + ((c_y_rl - y_y1_rl) * (1.0 / 3))),
//                        x_x1_real, y_y1_real);
////                }
//
//
////                path.cubicTo(
////                        (float) (center_x + (x_x1_real - center_x) *1.0/ 2), (float) (center_y - (y_y1_real-c_y_rl) * (3.0 / 4)-r_small),
////                        (float) (center_x + (x_x1_real - center_x)*1.0 / 2), (float) (center_y - (y_y1_real-c_y_rl) * (2.0 / 4)-r_small),
////                        x_x1_real, y_y1_real);
//
//                RectF rectF = new RectF(center_x - r_path, center_y - r_small, center_x, center_y + r_small);
//
//                float abs_angle = (float) (Math.toDegrees(Math.atan(y_y1_rl / x_x1_rl)));
//
//                if (c_x_rl - r_small < 0) {
//                    path.addArc(rectF, abs_angle, 180 - abs_angle);
//                } else {
//                    path.addArc(rectF, 180 - abs_angle, abs_angle);
//                }


                break;
            }
            case 4: {


                angle = (float) (Math.toDegrees(Math.atan(c_x_rl / c_y_rl)));

                y_y1_rl = Math.abs(c_y_rl - r_small);
                x_x1_rl = (float) Math.sqrt(r_small * r_small - y_y1_rl * y_y1_rl);
                x_x1_real = x_x1_rl + center_x;
                y_y1_real = (float) (center_y + c_y_rl);

                path.addArc(rect, 90, -angle);
//
                if (c_y_rl + arc_rate * 6.0 / 3 > r_path) {
//                    path.lineTo(x_x1_real, y_y1_real);
                    arc_rate = (int) ((r_path - c_y_rl) * 1.0 / 2);
                }

//                path.cubicTo(
//                        (float) (x_x1_real + arc_rate), (float) (y_y1_real + ((c_y_rl + y_y1_rl) * (2.0 / 3))),
//                        (float) (x_x1_real + arc_rate), (float) (y_y1_real + ((c_y_rl + y_y1_rl) * (1.0 / 3))),
//                        x_x1_real, y_y1_real);
                path.cubicTo(
                        (float) (x_x1_real + ((c_x_rl - x_x1_rl) * (2.0 / 3))), (float) (y_y1_real - arc_rate),
                        (float) (x_x1_real + ((c_x_rl - x_x1_rl) * (1.0 / 3))), (float) (y_y1_real - arc_rate),
                        x_x1_real, y_y1_real);
//
//                path.lineTo(x_x1_real, y_y1_real);
//
//
//                path.lineTo(center_x, center_y);
                float abs_angle = (float) (Math.toDegrees(Math.atan(x_x1_rl / y_y1_rl)));
                RectF rectF = new RectF(center_x - r_small, center_y, center_x + r_small, center_y + r_path);
                if (c_y_rl - r_small >= 0) {
                    path.addArc(rectF, 90 - abs_angle, abs_angle);
                } else {
                    path.addArc(rectF, -90 + abs_angle, 180 - abs_angle);
                }


                path.lineTo(center_x, center_y + r_path);


//                path.lineTo(center_x, center_y);
//                RectF rectF1 = new RectF(center_x - r_path, center_y - r_small, center_x, center_y + r_small);
//                path.addArc(rectF1, 0, 180);
//
////                path.cubicTo(center_x, center_y, center_x + r_small/2, center_y - r_small,center_x + r_small,center_y - r_small);
//                path.lineTo(center_x - r_path, center_y);
//
////                log(c_x_rl+"  "+r_path);
//                if (c_x_rl >= r_path - SideInstance) {
////                    Toast.makeText(getContext(),"down",Toast.LENGTH_LONG).show();
////                    log("down ######");
//                    down_press = true;
//                    up_press = false;
//
//                } else {
//                    up_press = false;
//                    down_press = false;
//                }
                break;
            }
        }


        canvas.drawPath(path, paint_point);

//        canvas.drawCircle(c_x_real, c_y_real, r, paint_point);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        _width = getWidth();
        _height = getHeight();
//        log("w=" + _width + " h=" + _width);

        if (ontouch) {
        draw_point(canvas);//绘制按压点
        } else {

        }


    }


}
