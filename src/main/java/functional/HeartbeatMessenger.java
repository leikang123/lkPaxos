package functional;

import cocagne.paxos.essential.ProposalID;
import cocagne.paxos.practical.PracticalMessenger;
// 心跳消息者继承实际消息者
public interface HeartbeatMessenger extends PracticalMessenger {
	// 发送心跳
	public void sendHeartbeat( ProposalID leaderProposalID);
	 // 附表
	public void schedule(long millisecondDelay, HeartbeatCallback callback);
	// 领导职位
	public void onLeadershipLost();
	// 领导职位变更，就是重新选举主节点
	public void onLeadershipChange(String previousLeaderUID, String newLeaderUID);
}
