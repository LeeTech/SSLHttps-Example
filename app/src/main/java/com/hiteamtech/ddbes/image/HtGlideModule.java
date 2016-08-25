package com.hiteamtech.ddbes.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * ClassName: HtGlideModule
 * Description: 图片缓存配置
 * author junli Lee mingyangnc@163.com
 * date 2015/11/19 11:48
 */
public class HtGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        // 内部磁盘私用缓存
        glideBuilder.setDiskCache(
                new ExternalCacheDiskCacheFactory(context, "glide_cache", 50 * 1024 * 1024));
        // 内存缓存策略
        glideBuilder.setMemoryCache(new LruResourceCache(20 * 1024 * 1024));
        // 位图的池的大小
        glideBuilder.setBitmapPool(new LruBitmapPool(20 * 1024 * 1024));
        // Bitmap Format
        glideBuilder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
