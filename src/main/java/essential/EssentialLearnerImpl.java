package essential;

import java.util.HashMap;

/**
 * 学习者类实现学习者接口
 * @author mac1094
 *
 */
public class EssentialLearnerImpl implements EssentialLearner {
	// 内部提议类
	class Proposal {
		// 接受者计数
		int    acceptCount;
		// 保留计数
		int    retentionCount;
		// 对象值
		Object value;
		// 内部类构造方法
		Proposal(int acceptCount, int retentionCount, Object value) {
			this.acceptCount    = acceptCount;
			this.retentionCount = retentionCount;
			this.value          = value;
		}
	}

	// 定义信使，也就是发送消息的通道，发送者
	private final EssentialMessenger      messenger;
	// 学习者的人数，或是总节点人数
	private final int                     quorumSize;
	// 学习者收到的决定，来自各种提议ID，K-v，提议者
	private HashMap<ProposalID, Proposal> proposals       = new HashMap<ProposalID, Proposal>();
	// 提议ID号来自接受者
	private HashMap<String,  ProposalID>  acceptors       = new HashMap<String, ProposalID>();
	// 对象值为空
	private Object                        finalValue      = null;
	// 最终的提议者编号为空
	private ProposalID                    finalProposalID = null;

	// 构造方法
	public EssentialLearnerImpl( EssentialMessenger messenger, int quorumSize ) {
		this.messenger  = messenger;
		this.quorumSize = quorumSize;
	}
   // 判断数据值是否完整
	@Override
	public boolean isComplete() {
		// 返回最终的值不为空
		return finalValue != null;
	}
	//接受者接收信息各种UID
	@Override
	public void receiveAccepted(String fromUID, ProposalID proposalID,
			Object acceptedValue) {
		
		if (isComplete())
			return;

		ProposalID oldPID = acceptors.get(fromUID);
		
		if (oldPID != null && !proposalID.isGreaterThan(oldPID))
			return;
		
		acceptors.put(fromUID, proposalID);

		if (oldPID != null) {
			Proposal oldProposal = proposals.get(oldPID);
			oldProposal.retentionCount -= 1;
			if (oldProposal.retentionCount == 0)
				proposals.remove(oldPID);
		}
        
		if (!proposals.containsKey(proposalID))
			proposals.put(proposalID, new Proposal(0, 0, acceptedValue));

		Proposal thisProposal = proposals.get(proposalID);	
		
		thisProposal.acceptCount    += 1;
		thisProposal.retentionCount += 1;
        
        if (thisProposal.acceptCount == quorumSize) {
        	finalProposalID = proposalID;
        	finalValue      = acceptedValue;
        	proposals.clear();
        	acceptors.clear();
        	
        	messenger.onResolution(proposalID, acceptedValue);
        }
	}

	public int getQuorumSize() {
		return quorumSize;
	}

	@Override
	public Object getFinalValue() {
		return finalValue;
	}

	@Override
	public ProposalID getFinalProposalID() {
		return finalProposalID;
	}
}
