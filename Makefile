# Copyright (c) 2023 AccelByte Inc. All Rights Reserved
# This program is made available under the terms of the MIT License.

SHELL := /bin/bash

.PHONY: build samples

publish:
	docker run -t --rm -u  $$(id -u):$$(id -g) -v $$(pwd):/data/ -w /data/ -e GRADLE_USER_HOME=/data/.gradle \
			-e PUBLISH_OSSRH_USERNAME="$$PUBLISH_OSSRH_USERNAME" -e PUBLISH_OSSRH_PASSWORD="$$PUBLISH_OSSRH_PASSWORD" \
			-e PUBLISH_ASC_KEY="$$(cat "$$PUBLISH_ASC_KEY_FILE_PATH")" -e PUBLISH_ASC_KEY_PASSWORD="$$PUBLISH_ASC_KEY_PASSWORD" \
			gradle:7.5.1-jdk8 gradle --console=plain -i --no-daemon publishToSonatype
