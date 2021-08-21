package essential;

/**
 * 该类实现基本接受者的接口以及功能
 * @author mac1094
 *
 */
// 定义一个实现类，实现接口
public class EssentialAcceptorImpl implements EssentialAcceptor {
	// 基本信使
	protected EssentialMessenger messenger;
	// 承若ID号（接受提议者发送的ID号）
	protected ProposalID         promisedID;
	// 接受者ID号（自己的ID号）
	protected ProposalID         acceptedID;
	// 接受者的值（自己的值）
	protected Object             acceptedValue;
   // 构造方法信使，发送消息的信道
	public EssentialAcceptorImpl( EssentialMessenger messenger ) {
		this.messenger = messenger;
	}
   /**
    * 接口的功能实现
    */
	@Override
	public void receivePrepare(String fromUID, ProposalID proposalID) {
		// 如果承若ID号不为空和 ID号是正确的
		if (this.promisedID != null && proposalID.equals(promisedID)) { 
			// 信使发送各种ID号
			messenger.sendPromise(fromUID, proposalID, acceptedID, acceptedValue);
		}
		// 否则如果承若号为空 或者 超过规则
		else if (this.promisedID == null || proposalID.isGreaterThan(promisedID)) {
			// 承若号就是新的承若号
			promisedID = proposalID;
			// 信使发送各种ID号以及值
			messenger.sendPromise(fromUID, proposalID, acceptedID, acceptedValue);
		}
	}
    /**
     * 实现接口的功能
     */
	@Override
	public void receiveAcceptRequest(String fromUID, ProposalID proposalID,Object value) {
		// 如果承若ID为空或者ID超规则或者是正确的ID号，就是一个新的ID号
		if (promisedID == null || proposalID.isGreaterThan(promisedID) || proposalID.equals(promisedID)) {
			promisedID    = proposalID;
			acceptedID    = proposalID;
			acceptedValue = value;
			// 信使发送接受者ID号以及值
			messenger.sendAccepted(acceptedID, acceptedValue);
		}
	}
   // getter/settter方法
	public EssentialMessenger getMessenger() {
		return messenger;
	}

	public ProposalID getPromisedID() {
		return promisedID;
	}

	public ProposalID getAcceptedID() {
		return acceptedID;
	}

	public Object getAcceptedValue() {
		return acceptedValue;
	}

}
