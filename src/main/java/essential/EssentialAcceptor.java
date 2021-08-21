package essential;

/**
 * 基本接受者接口
 * @author mac1094
 *
 */
// 定义一个接口为基本接受者
public interface EssentialAcceptor {
	/**
	 * 接受准备功能
	 * @param fromUID 来自提议者的UID号
	 * @param proposalID 提议ID号
	 */
	public void receivePrepare(String fromUID, ProposalID proposalID);
	/**
	 * 接收接受请求
	 * @param fromUID 来自提议者的UID号
	 * @param proposalID 提议ID号
	 * @param value 对象值
	 */
	public void receiveAcceptRequest(String fromUID, ProposalID proposalID, Object value);
}
