package org.ali.rockermq.message;

import org.apache.rocketmq.common.message.MessageExt;

public interface MessageProcessor {
	boolean handle(MessageExt messageExt);
}
