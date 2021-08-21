package essential;

/**
 * 基本提议接口
 * @author mac1094
 *
 */
public interface EssentialProposer {
	 // 设置提案号ID和对应的值
	public void setProposal(Object value);
	// 准备阶段
	public void prepare();
	// 接受承若
	public void receivePromise(String fromUID, ProposalID proposalID, ProposalID prevAcceptedID, Object prevAcceptedValue);

}
