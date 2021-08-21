
package essential;


/**
 * 定义的送信人，信使接口
 * @author mac1094
 *
 */
public interface EssentialMessenger {
   // 发送准备功能
	public void sendPrepare(ProposalID proposalID);
   // 发送提议
	public void sendPromise(String proposerUID, ProposalID proposalID, ProposalID previousID, Object acceptedValue);
  // 发送接受
	public void sendAccept(ProposalID proposalID, Object proposalValue);
   // 发送己经接受过的
	public void sendAccepted(ProposalID proposalID, Object acceptedValue);
	// 决定
	public void onResolution(ProposalID proposalID, Object value);
}
	