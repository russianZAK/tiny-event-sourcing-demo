package ru.quipy.api.project.user

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "aggregate-project-user")
class ProjectUserAggregate : Aggregate
