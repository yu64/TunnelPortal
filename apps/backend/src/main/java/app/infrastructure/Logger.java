package app.infrastructure;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Logger
{
  private Level limitLevel;

  public Logger(
    Level limitLevel
  )
  {
    this.limitLevel = limitLevel;
  }

  private void log(Level level, Object... values)
  {
    // 指定レベル以上であれば、表示できる
    if( !(this.limitLevel.ordinal() <= level.ordinal()))
    {
      return;
    }

    // リスト変換
    List<Object> args = new ArrayList<>();
    
    // 日付追加・レベル・引数を追加
    args.add(OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    args.add(level);
    args.addAll(List.of(values));

    // テキスト変換
    String text = Stream.of(args)
      .map(v -> (v == null ? "null" : v))
      .map(v -> ((v instanceof Class c) ? c.getSimpleName() : v))
      .map(v -> ((v instanceof Enum e) ? e.name() : v))
      .map(v -> v.toString())
      .collect(Collectors.joining("\t"))
      ;

    // 出力
    System.out.println(text);
  }

  public void err(Object... values)
  {
    this.log(Level.Error, values);
  }

  public void warn(Object... values)
  {
    this.log(Level.Warn, values);
  }

  public void info(Object... values)
  {
    this.log(Level.Info, values);
  }

  public void debug(Object... values)
  {
    this.log(Level.Debug, values);
  }

  /**
   * ログレベル
   */
  public static enum Level
  {
    Error,
    Warn,
    Info,
    Debug,
  }
  
}
