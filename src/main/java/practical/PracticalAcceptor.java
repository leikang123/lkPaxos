package practical;

import cocagne.paxos.essential.EssentialAcceptor;
import cocagne.paxos.essential.ProposalID;
// 实际接受者
public interface PracticalAcceptor extends EssentialAcceptor {
     // 持久性要求
	public abstract boolean persistenceRequired();
     // 恢复
	public abstract void recover(ProposalID promisedID, ProposalID acceptedID,
			Object acceptedValue);
      // 持续，坚持
	public abstract void persisted();

	public abstract boolean isActive();

	public abstract void setActive(boolean active);

}