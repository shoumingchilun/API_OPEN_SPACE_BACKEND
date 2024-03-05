package com.chilun.apiopenspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chilun.apiopenspace.model.Masked.InterfaceAccessMasked;
import com.chilun.apiopenspace.model.entity.InterfaceAccess;

import java.util.List;

/**
 * @author 齿轮
 * @description 针对表【interface_access(接口申请表)】的数据库操作Service
 * @createDate 2024-02-15 20:36:55
 */
public interface InterfaceAccessService extends IService<InterfaceAccess> {
    /**
     * 接口申请
     *
     * @param interfaceId 被申请接口id
     * @return 接口详情，包含访问码和密钥
     */
    InterfaceAccess InterfaceApply(Long userid, Long interfaceId);

    /**
     * 访问码废除
     *
     * @param accesskey 访问码
     */
    void accesskeyAbolish(String accesskey);

    /**
     * 改变验证形式，默认为强校验
     *
     * @param type 访问码
     */
    InterfaceAccess changeVerifyType(String accesskey, Integer type);

    /**
     * 获取脱敏的接口信息
     *
     * @param interfaceAccess 待脱敏对象
     * @return 已脱敏对象
     */
    InterfaceAccessMasked getInterfaceAccessMasked(InterfaceAccess interfaceAccess);

    /**
     * 获取脱敏的接口信息
     *
     * @param interfaceAccessList 待脱敏对象集合
     * @return 已脱敏对象集合
     */
    List<InterfaceAccessMasked> getInterfaceAccessMasked(List<InterfaceAccess> interfaceAccessList);

    /**
     * 获取脱敏的接口信息
     *
     * @param callTimes       增加总调用次数
     * @param failedCallTimes 增加失败调用次数
     * @param accesskey       访问码
     */
    void addCallTimes(int callTimes, int failedCallTimes, String accesskey);

    void batchAddCallTimes(List<BatchAddItem> list);

    class BatchAddItem {
        private int callTimes;
        private int failedCallTimes;
        private String accesskey;

        public BatchAddItem(int callTimes, int failedCallTimes, String accesskey) {
            this.callTimes = callTimes;
            this.failedCallTimes = failedCallTimes;
            this.accesskey = accesskey;
        }

        public BatchAddItem() {
        }

        public int getCallTimes() {
            return callTimes;
        }

        public void setCallTimes(int callTimes) {
            this.callTimes = callTimes;
        }

        public int getFailedCallTimes() {
            return failedCallTimes;
        }

        public void setFailedCallTimes(int failedCallTimes) {
            this.failedCallTimes = failedCallTimes;
        }

        public String getAccesskey() {
            return accesskey;
        }

        public void setAccesskey(String accesskey) {
            this.accesskey = accesskey;
        }
    }
}
