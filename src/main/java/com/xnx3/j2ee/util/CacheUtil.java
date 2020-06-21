package com.xnx3.j2ee.util;

/**
 * 缓存工具。
 * 若使用Redis，在 application.properties 中配置了redis，那么这里是使用redis进行的缓存
 * 如果没有使用redis，那么这里使用的是 Hashmap 进行的缓存
 * @author 管雷鸣
 * @deprecated 请使用 {@link com.xnx3.CacheUtil}
 */
public class CacheUtil extends com.xnx3.CacheUtil{
}
