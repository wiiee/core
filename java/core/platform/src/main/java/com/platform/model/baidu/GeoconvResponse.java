package com.platform.model.baidu;

import java.util.List;

/**
 * Created by wiiee on 9/23/2017.
 */
public class GeoconvResponse {
    private int status;
    private List<GeoconvResult> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GeoconvResult> getResult() {
        return result;
    }

    public void setResult(List<GeoconvResult> result) {
        this.result = result;
    }

    public static class GeoconvResult {
        private double x;
        private double y;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }
}
