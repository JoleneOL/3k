package org.jolene.threek.repository;

import org.jolene.threek.entity.ProvideOrder;
import org.jolene.threek.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Jolene
 */
@Repository
public interface ProvideOrderRepository extends JpaRepository<ProvideOrder, Long> {

    /**
     * 获取未完成的订单数量
     *
     * @param user 用户
     * @return 订单数量
     */
    @Query("select count(o) from ProvideOrder as o where o.owner=?1 and o.orderStatus <> org.jolene.threek.entity.support.ProvideStatus.finished")
    int countOrderingByUse(User user);

}
