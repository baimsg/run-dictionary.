package com.baimsg.bean

data class ProxyItem(
    val state: Int,
    val msg: String,
    val data: List<ProxyInfo>
)

data class ProxyInfo(
    val ip: String,
    val port: Int,
    val ttl: Int
)