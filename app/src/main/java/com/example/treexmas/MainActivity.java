package com.example.treexmas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    private ImageView christmasTreeImageView;
    private TextView viewtextname;
    private int lightColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        christmasTreeImageView = findViewById(R.id.TreeView);
        viewtextname = findViewById(R.id.viewname);
        // Bắt đầu tự động cập nhật màu
        startColorChange();

    }

    private void startColorChange() {
        // Tạo animator để thay đổi màu sắc của quả bóng
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                Color.rgb(255, 0, 0),      // Màu đỏ
                Color.rgb(0, 255, 0),      // Màu xanh lá cây
                Color.rgb(0, 0, 255),      // Màu xanh dương
                Color.rgb(255, 255, 0),    // Màu vàng
                Color.rgb(255, 0, 255),    // Màu hồng
                Color.rgb(0, 255, 255),    // Màu cyan
                Color.rgb(128, 0, 128),    // Màu tím
                Color.rgb(255, 165, 0),    // Màu da cam
                Color.rgb(0, 128, 0),      // Màu xanh lá cây đậm
                Color.rgb(128, 128, 128)   // Màu xám
        );
        colorAnimator.setDuration(1000); // Thời gian để chuyển màu (3 giây)
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE); // Lặp vô hạn

        colorAnimator.addUpdateListener(animation -> {
            int animatedColor = (int) animation.getAnimatedValue();
            drawChristmasTreeWithLightColor(animatedColor);
        });

        colorAnimator.start();

    }

    private void drawChristmasTreeWithLightColor(int lightColor) {
        Bitmap bitmap = drawChristmasTree(lightColor);
        christmasTreeImageView.setImageBitmap(bitmap);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // Find the root view (assuming ChristmasTreeView is part of the activity layout)
            View rootView = findViewById(android.R.id.content);

            // Hiển thị thông báo khi người dùng chạm vào màn hình
            showSnackbar(rootView);
        }
        return true;
    }


    private void showSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, "Merry Christmas", Snackbar.LENGTH_SHORT);

        snackbar.setAction("OK", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
        snackbar.show();
    }


    private Bitmap drawChristmasTree(int lightColor) {
        int width = 500; // Độ rộng của ảnh
        int height = 1000; // Độ cao của ảnh

        viewtextname.setText("Cao Bao Khanh\nNguyen Hoang Ton\nPham Huy Hoang");
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Vẽ thân cây
        Paint trunkPaint = new Paint();
        trunkPaint.setColor(Color.rgb(139, 69, 19)); // Màu nâu
        canvas.drawRect(200, 500, 300, 900, trunkPaint);

        // Vẽ lá cây
        Paint leavesPaint = new Paint();
        leavesPaint.setColor(Color.rgb(0, 128, 0)); // Màu xanh lá cây
        drawTriangle(canvas, 250, 200, 500, leavesPaint);
        drawTriangle(canvas, 250, 125, 400, leavesPaint);
        drawTriangle(canvas, 250, 50, 300, leavesPaint);
        // Vẽ các bóng đèn
        drawStar(canvas, 250, 55, 80,     lightColor);
        drawLight(canvas, 290, 350, 17, lightColor);
        drawLight(canvas, 190, 450, 17, lightColor);
        drawLight(canvas, 300, 500, 17, lightColor);
        drawLight(canvas, 350, 600, 17, lightColor);
        drawLight(canvas, 150, 600, 17, lightColor);
        drawLight(canvas, 220, 180, 17, lightColor);
        drawLight(canvas, 210, 270, 17, lightColor);


        return bitmap;
    }

    private void drawLight(Canvas canvas, float x, float y, float radius, int color) {
        Paint lightPaint = new Paint();
        lightPaint.setColor(color);
        canvas.drawCircle(x, y, radius, lightPaint);
    }



   private void drawText(Canvas canvas, float x, float y, String text, int color) {
        Paint textPaint = new Paint();
        textPaint.setColor(color);
        textPaint.setTextSize(35); // Kích thước văn bản
        canvas.drawText(text, x, y, textPaint);
    }

    private void drawTriangle(Canvas canvas, float x, float y, float size, Paint paint) {
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x - size / 2, y + size);
        path.lineTo(x + size / 2, y + size);
        path.lineTo(x, y);
        path.close();

        canvas.drawPath(path, paint);
    }
    private void drawStar(Canvas canvas, float x, float y, float size, int color) {
        Path path = new Path();
        float angle = (float) (3 / 5);  // Adjusted the angle

        float radiusOuter = size / 2;
        float radiusInner = radiusOuter /2 ;  // Adjusted the inner radius

        for (int i = 0; i < 10; i++) {
            float xOuter = x + (float) (radiusOuter * Math.cos(angle));
            float yOuter = y + (float) (radiusOuter * Math.sin(angle ));
            float xInner = x + (float) (radiusInner * Math.cos(angle  - 4.5 / 5 ) );
            float yInner = y + (float) (radiusInner * Math.sin(angle - 4.5 / 5));

            if (i == 0) {
                path.moveTo(xOuter, yOuter);
            } else {
                path.lineTo(xInner, yInner);
                path.lineTo(xOuter, yOuter);
            }

            angle += 2 * 4.5 / 5;
        }

        path.close();

        Paint starPaint = new Paint();
        starPaint.setColor(color);

        canvas.drawPath(path, starPaint);
    }


}