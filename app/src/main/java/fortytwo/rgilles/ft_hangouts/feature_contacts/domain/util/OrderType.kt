package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
