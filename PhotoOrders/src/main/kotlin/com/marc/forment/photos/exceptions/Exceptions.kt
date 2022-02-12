package com.marc.forment.photos.exceptions

import com.marc.forment.photos.entities.OrderState
import java.util.Date

class OrderNotFoundException(orderId: Long) : Exception("Order $orderId not found.")
class PhotographerNotFoundException(photographerId: Long) : Exception("Photographer $photographerId not found.")
class InvalidState(invalidState: OrderState, expectedState: OrderState) : Exception("Order is invalid state $invalidState. Expected $expectedState for this operation.")
class NotInBusinessHours(date: Date) : Exception("Provided datetime $date not in business hours(08:00-20:00) ")