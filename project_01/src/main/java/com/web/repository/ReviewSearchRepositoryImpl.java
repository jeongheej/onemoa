package com.web.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.web.entity.Notice;
import com.web.entity.QMember;
import com.web.entity.QNotice;
import com.web.entity.QReview;
import com.web.entity.Review;

@Service
public class ReviewSearchRepositoryImpl
extends QuerydslRepositorySupport implements ReviewSearchRepository {

	
	
	

	public ReviewSearchRepositoryImpl() {
		super(Review.class);
		
	}

	@Override
	public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
		JPQLQuery<Review> jpqlQuery = from(QReview.review);
		jpqlQuery.leftJoin(QMember.member).on(QReview.review.writer.eq(QMember.member));
		JPQLQuery<Tuple> tuple = jpqlQuery.select(QReview.review, QMember.member);
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression expression = QReview.review.rno.gt(0L); //0보다 큰?
		booleanBuilder.and(expression);
		
		if(type != null) {
			String[] typeArr = type.split("");
			BooleanBuilder conditionBuilder = new BooleanBuilder();
			for(String t: typeArr) {
				switch (t) {
				case "t": conditionBuilder.or(QReview.review.title.contains(keyword));
					break;
					
				case "w": conditionBuilder.or(QMember.member.name.contains(keyword));
				break;
			
				case "c": conditionBuilder.or(QReview.review.content.contains(keyword));
				break;
				
				case "tc": conditionBuilder.or(QReview.review.content.contains(keyword)
						.or(QReview.review.title.contains(keyword)));
				break;
				
			
				}
			}
			
			booleanBuilder.and(conditionBuilder);
		}
		
		tuple.where(booleanBuilder);
		Sort sort = pageable.getSort();
		sort.stream().forEach(order-> {
			Order direction = order.isAscending()? Order.ASC:Order.DESC;
			String prop = order.getProperty();
			PathBuilder orderByExpression = new PathBuilder<>(Review.class, "review");
			tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
		});
		
		tuple.groupBy(QReview.review);
		tuple.offset(pageable.getOffset()); //페이지 시작지점
		tuple.limit(pageable.getPageSize());
		List<Tuple> result= tuple.fetch();
		long count = tuple.fetchCount();
		
		
		return new PageImpl<Object[]>(result.stream()
				.map(t->t.toArray()).collect(Collectors.toList()), pageable, count);

	}

}
