# WXPrometheus

Implements own package to support the Prometheus protocol.

## Version History

### 1.0

Initial release.

### 2.0

Remove dependency and implementation of Log4J v1. Now, log messages are pushed to IS server logger directly. Also, tested for wM 10.15.

### 2.1

In Kubernetes and Microservices Runtime (MSR) environment, it make sense to have one entrypoint `/metrics` for scrapping. If you want to have both metrics ...

* MSR build-in and
* e.g. `WxPlatformInsigth` package, ...

you should set the Watt properties `watt.wx.prometheus.overwriteUrlAlias=true` and `watt.wx.prometheus.addBuiltInMetrics=true`

* `watt.wx.prometheus.overwriteUrlAlias=true` allows to overwrite the default MSR endpoint service with `WxPrometheus` service.
* `watt.wx.prometheus.addBuiltInMetrics=true` adds the default MSR endpoint service to the `WxPrometheus`metrics collector.
