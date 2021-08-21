package essential;

import java.util.HashSet;

/**
 * 基本提议者实现类
 */
public class EssentialProposerImpl implements EssentialProposer {

	// 送信者
	protected EssentialMessenger  messenger;
	// 提议者UID值
    protected String              proposerUID;
    // 人数大小
    protected final int           quorumSize;
    // 发送的建议ID号
    protected ProposalID          proposalID;
    protected Object              proposedValue      = null;
    protected ProposalID          lastAcceptedID     = null;
    protected HashSet<String>     promisesReceived   = new HashSet<String>();
    // 构造方法
    public EssentialProposerImpl(EssentialMessenger messenger, String proposerUID, int quorumSize) {
		this.messenger   = messenger;
		this.proposerUID = proposerUID;
		this.quorumSize  = quorumSize;
		this.proposalID  = new ProposalID(0, proposerUID);
	}

	@Override
	public void setProposal(Object value) {
    	// 如果提议者建议为空
		if ( proposedValue == null )
			// 建议值还是当前的
			proposedValue = value;
	}

	@Override
	public void prepare() {
		promisesReceived.clear();
		
		proposalID.incrementNumber();
		
		messenger.sendPrepare(proposalID);
	}
     // 接受承若方法
	@Override
	public void receivePromise(String fromUID, ProposalID proposalID,
			ProposalID prevAcceptedID, Object prevAcceptedValue) {

		if ( !proposalID.equals(this.proposalID) || promisesReceived.contains(fromUID) ) 
			return;
		
        promisesReceived.add( fromUID );

        if (lastAcceptedID == null || prevAcceptedID.isGreaterThan(lastAcceptedID))
        {
        	lastAcceptedID = prevAcceptedID;

        	if (prevAcceptedValue != null)
        		proposedValue = prevAcceptedValue;
        }
        
        if (promisesReceived.size() == quorumSize)
        	if (proposedValue != null)
        		messenger.sendAccept(this.proposalID, proposedValue);
	}

	public EssentialMessenger getMessenger() {

    	return messenger;
	}

	public String getProposerUID() {

    	return proposerUID;
	}

	public int getQuorumSize() {
		return quorumSize;
	}

	public ProposalID getProposalID() {

    	return proposalID;
	}

	public Object getProposedValue() {
		return proposedValue;
	}

	public ProposalID getLastAcceptedID() {

    	return lastAcceptedID;
	}
	
	public int numPromises() {

    	return promisesReceived.size();
	}
}
