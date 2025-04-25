package jpabook.japshop.domain.order;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.japshop.api.service.order.request.OrderSearchCondition;
import jpabook.japshop.api.service.order.response.OrderResponse;
import jpabook.japshop.domain.member.QMember;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public List<OrderResponse> findAll(OrderSearchCondition orderSearchCondition) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return queryFactory
            .select(
                Projections.constructor(OrderResponse.class,
                    order.id,
                    order.member.name,
                    order.orderDate,
                    order.orderStatus,
                    order.member.address
                )
            )
            .from(order)
            .join(order.member, member)
            .where(
                memberNameEq(orderSearchCondition.memberName()),
                orderStatusEq(orderSearchCondition.status())
            )
            .fetch();
    }

    private BooleanExpression memberNameEq(String memberName) {
        return memberName == null ? null : QOrder.order.member.name.eq(memberName);
    }

    private BooleanExpression orderStatusEq(OrderStatus orderStatus) {
        return orderStatus == null ? null : QOrder.order.orderStatus.eq(orderStatus);
    }
}
