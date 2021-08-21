package essential;

/**
 * 定义一个学习者接口
 * @author mac1094
 *
 */
public interface EssentialLearner {
   // 判断是否是完整的
	public boolean isComplete();
	// 接受者的ID号
	public void receiveAccepted(String fromUID, ProposalID proposalID, Object acceptedValue);
	// 获取各种值
	public Object getFinalValue();
    // 获取提议案的ID号
	ProposalID getFinalProposalID();
}
