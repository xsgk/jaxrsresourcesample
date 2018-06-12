# jaxrsresourcesample
## Overview
JAX-RS検証用のサーバー。[JAX-RS検証用クライアント](https://github.com/xsgk/jaxrsclientsample)とセットで使用することを想定。Base64エンコードされた画像ファイルのアップロードをJSON形式のリクエストで受け付け、同じくBase64エンコードされた画像ファイルをレスポンスとしてJSON形式で応答。

## How to use
クローンされたリポジトリへ移動し、

```
# cd jaxrsresourcesample
```

ビルド用シェルを実行。[WebSphere Liberty](https://hub.docker.com/_/websphere-liberty/) コンテナをベースとした検証用コンテナを作成し、起動までが自動で実行される。

```
# ./build.sh
```
