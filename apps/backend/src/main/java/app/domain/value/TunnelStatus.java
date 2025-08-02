package app.domain.value;

public enum TunnelStatus
{
  Running,
  Stopped,
  Unknown,

  ;

  public boolean isHigherPriorityThan(TunnelStatus right)
  {
    // 最初に定義されているほど優先
    return this.ordinal() < right.ordinal();
  }
}
