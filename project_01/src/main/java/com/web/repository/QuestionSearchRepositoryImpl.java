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
import com.web.entity.QMember;
import com.web.entity.QQuestion;
import com.web.entity.QQuestionReply;
import com.web.entity.QReview;
import com.web.entity.Question;
import com.web.entity.Review;

public class QuestionSearchRepositoryImpl 
extends QuerydslRepositorySupport implements QuestionSearchRepository{

	public QuestionSearchRepositoryImpl() {
		super(Question.class);
		
	}

	@Override
	public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
		JPQLQuery<Question> jpqlQuery = from(QQuestion.question);
		jpqlQuery.leftJoin(QMember.member).on(QQuestion.question.writer.eq(QMember.member));
		jpqlQuery.leftJoin(QQuestionReply.questionReply).on(QQuestionReply.questionReply.qno.eq(QQuestion.question));
		JPQLQuery<Tuple> tuple = jpqlQuery.select(QQuestion.question, QMember.member, QQuestionReply.questionReply.count());
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression expression = QQuestion.question.qno.gt(0L); //0보다 큰?
		booleanBuilder.and(expression);
		
		if(type != null) {
			String[] typeArr = type.split("");
			BooleanBuilder conditionBuilder = new BooleanBuilder();
			for(String t: typeArr) {
				switch (t) {
				case "t": conditionBuilder.or(QQuestion.question.title.contains(keyword));
					break;
					
				case "w": conditionBuilder.or(QMember.member.name.contains(keyword));
				break;
			
				case "c": conditionBuilder.or(QQuestion.question.content.contains(keyword));
				break;
				
				case "tc": conditionBuilder.or(QQuestion.question.content.contains(keyword)
						.or(QQuestion.question.title.contains(keyword)));
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
			PathBuilder orderByExpression = new PathBuilder<>(Question.class, "question");
			tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
		});
		
		tuple.groupBy(QQuestion.question);
		tuple.offset(pageable.getOffset()); //페이지 시작지점
		tuple.limit(pageable.getPageSize());
		List<Tuple> result = tuple.fetch();
		long count = tuple.fetchCount();
		
		
		return new PageImpl<Object[]>(result.stream()
				.map(t->t.toArray()).collect(Collectors.toList()), pageable, count);
	
	}

}
