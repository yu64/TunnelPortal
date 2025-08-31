package app.util;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

public final class ClassUtil
{

  private ClassUtil() {}


  /**
	 * 指定された複数のパッケージ内の、指定された親クラスの具象サブクラスをすべて検索します。
	 *
	 * @param targetPackages スキャン対象のパッケージオブジェクトのリスト
	 * @param parentClasses 検索対象の親クラスまたはインターフェースのリスト
	 * @return 条件に一致するクラスのSet
	 */
	public static Set<Class<?>> findSubclasses(List<Package> targetPackages, List<Class<?>> parentClasses)
	{
		String[] packageNames = targetPackages.stream()
			.map(Package::getName)
			.toArray(String[]::new);

		List<String> parentClassNames = parentClasses.stream()
			.map(c -> c.getName())
			.collect(Collectors.toList());
			
      

		Set<Class<?>> foundClasses = new HashSet<>();

		try(
			ScanResult scanResult = new ClassGraph()
				.enableClassInfo()
				.acceptPackages(packageNames)
				.scan()
		)
		{
			for(String parentClassName : parentClassNames)
			{
				Set<ClassInfo> subclasses = scanResult
					.getClassInfo(parentClassName)
					.getClassesImplementing()
					.stream()
					.collect(Collectors.toSet());

				for(ClassInfo classInfo : subclasses)
				{
					if(!classInfo.isInterface() && !Modifier.isAbstract(classInfo.getModifiers()))
					{
						foundClasses.add(classInfo.loadClass());
					}
				}
			}
		}

		return foundClasses;
	}

  
}