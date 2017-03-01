/**
 *
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.MessageEvaluationContext;

import java.util.List;

/**
 * ClientIdFilterDispatchPolicy dispatches messages in a topic to a given
 * client. Then the message with a "PTP_CLIENTID" property, can be received by a
 * mqtt client with the same clientId.
 *
 * @author kimmking (kimmking.cn@gmail.com)
 * @date 2013-12-20
 * @org.apache.xbean.XBean
 */
public class ClientIdFilterDispatchPolicy extends SimpleDispatchPolicy {

    public static final String PTP_CLIENTID = "ptp_client_id";
    public static final String PTP_SUFFIX = ".ptp";

    private String ptpClientIdPropertyName = PTP_CLIENTID;
    private String ptpTopicSuffix = PTP_SUFFIX;

    public boolean dispatch(MessageReference node, MessageEvaluationContext msgContext, List<Subscription> consumers) throws Exception {

        ActiveMQDestination destination = node.getMessage().getDestination();

        if (!destination.isTopic() || !destination.getQualifiedName().endsWith(this.ptpTopicSuffix))
            return super.dispatch(node, msgContext, consumers);

        Object clientId = node.getMessage().getProperty(ptpClientIdPropertyName);

        if (clientId == null) return super.dispatch(node, msgContext, consumers);

        int count = 0;
        for (Subscription sub : consumers) {
            // Don't deliver to browsers
            if (sub.getConsumerInfo().isBrowser()) {
                continue;
            }
            // Only dispatch to interested subscriptions
            if (!sub.matches(node, msgContext)) {
                sub.unmatched(node);
                continue;
            }
            if (clientId.equals(sub.getContext().getClientId())) {
                sub.add(node);
                count++;
            } else {
                sub.unmatched(node);
            }
        }

        return count > 0;
    }

    public String getPtpClientIdPropertyName() {
        return ptpClientIdPropertyName;
    }

    public void setPtpClientIdPropertyName(String ptpClientIdPropertyName) {
        this.ptpClientIdPropertyName = ptpClientIdPropertyName;
    }

    public String getPtpTopicSuffix() {
        return ptpTopicSuffix;
    }

    public void setPtpTopicSuffix(String ptpTopicSuffix) {
        this.ptpTopicSuffix = ptpTopicSuffix;
    }

}