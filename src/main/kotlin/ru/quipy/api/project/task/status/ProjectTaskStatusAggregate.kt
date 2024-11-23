package ru.quipy.api.project.task.status

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "aggregate-project-task-status")
class ProjectTaskStatusAggregate : Aggregate
