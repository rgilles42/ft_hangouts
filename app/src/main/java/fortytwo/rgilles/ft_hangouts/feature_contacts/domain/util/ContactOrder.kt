package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util

sealed class ContactOrder(val orderType: OrderType) {
    class LastName(orderType: OrderType): ContactOrder(orderType)
    class FirstName(orderType: OrderType): ContactOrder(orderType)
}
