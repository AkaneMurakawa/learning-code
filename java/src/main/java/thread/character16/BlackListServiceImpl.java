package thread.character16;

import java.util.Map;

/**
 * CopyOnWrite 应用示例
 * <p>
 * 需要各位小伙伴特别特别注意一个问题，此处的场景是每晚凌晨 "黑名单"定 时更新，
 * 原因是CopyOnWrite容器有数据一致性的问题，它只能保证最终数据一致性。
 * <p>
 * 所以如果我们希望写入的数据马上能准确地读取，请不要使用CopyOnWrite容器。
 */
public class BlackListServiceImpl {

    // 减少扩容开销。根据实际需要，初始化CopyOnWriteMap的大小，避免写时CopyOnWriteMap扩容的开销
    private static CopyOnWriteMap<String, Boolean> blackListMap =
            new CopyOnWriteMap<String, Boolean>(1000);

    public static boolean isBlackList(String key) {
        return blackListMap.get(key) == null ? false : true;
    }

    public static void addBlackList(String id) {
        blackListMap.put(id, Boolean.TRUE);
    }

    /**
     * 批量添加黑名单
     * (使用批量添加。因为每次添加，容器每次都会进行复制，所以减少添加次数，可以减少容器的复制次数。
     * 如使用上面代码里的addBlackList方法)
     *
     * @param keys
     */
    public static void addBlackList(Map<String, Boolean> keys) {
        blackListMap.putAll(keys);
    }


}
