package com.keepllly.auth.utils;

import com.keepllly.auth.utils.enums.ImageMimeTypeEnum;
import com.keepllly.auth.utils.enums.ImageSourceEnum;
import java.util.Objects;

public class Image<T> {

    private T data;

    private ImageSourceEnum srcType;

    private ImageMimeTypeEnum mimeType;

    private String afterScaleWidth;

    private String afterScaleHeight;

    private boolean scale;

    private Double scaleX;

    private Double scaleY;

    public Image() {}

    public Image(T data, ImageSourceEnum srcType) {
        this.data = data;
        this.srcType = srcType;
    }

    public Image(T data, ImageSourceEnum srcType, ImageMimeTypeEnum mimeType) {
        this.data = data;
        this.srcType = srcType;
        this.mimeType = mimeType;
    }

    public Image(T data, ImageSourceEnum srcType, ImageMimeTypeEnum mimeType, boolean scale, Double scaleX, Double scaleY) {
        this.data = data;
        this.srcType = srcType;
        this.mimeType = mimeType;
        this.scale = scale;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public Image(
        T data,
        ImageSourceEnum srcType,
        ImageMimeTypeEnum mimeType,
        String afterScaleWidth,
        String afterScaleHeight,
        boolean scale,
        Double scaleX,
        Double scaleY
    ) {
        this.data = data;
        this.srcType = srcType;
        this.mimeType = mimeType;
        this.afterScaleWidth = afterScaleWidth;
        this.afterScaleHeight = afterScaleHeight;
        this.scale = scale;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ImageSourceEnum getSrcType() {
        return srcType;
    }

    public void setSrcType(ImageSourceEnum srcType) {
        this.srcType = srcType;
    }

    public ImageMimeTypeEnum getMimeType() {
        return mimeType;
    }

    public void setMimeType(ImageMimeTypeEnum mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isScale() {
        return scale;
    }

    public void setScale(boolean scale) {
        this.scale = scale;
    }

    public Double getScaleX() {
        return scaleX;
    }

    public void setScaleX(Double scaleX) {
        this.scaleX = scaleX;
    }

    public Double getScaleY() {
        return scaleY;
    }

    public void setScaleY(Double scaleY) {
        this.scaleY = scaleY;
    }

    public String getAfterScaleWidth() {
        return afterScaleWidth;
    }

    public void setAfterScaleWidth(String afterScaleWidth) {
        this.afterScaleWidth = afterScaleWidth;
    }

    public String getAfterScaleHeight() {
        return afterScaleHeight;
    }

    public void setAfterScaleHeight(String afterScaleHeight) {
        this.afterScaleHeight = afterScaleHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return (
            scale == image.scale &&
            Objects.equals(data, image.data) &&
            srcType == image.srcType &&
            mimeType == image.mimeType &&
            Objects.equals(scaleX, image.scaleX) &&
            Objects.equals(scaleY, image.scaleY)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, srcType, mimeType, scale, scaleX, scaleY);
    }

    @Override
    public String toString() {
        return (
            "Image{" +
            "data='" +
            data +
            '\'' +
            ", srcType=" +
            srcType +
            ", mimeType=" +
            mimeType +
            ", scale=" +
            scale +
            ", scaleX=" +
            scaleX +
            ", scaleY=" +
            scaleY +
            '}'
        );
    }
}
