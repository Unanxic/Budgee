package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgee.R
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.Canvas
import com.example.budgee.ui.theme.Mint
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.ui.theme.TextSecondary
import com.example.budgee.utils.pulsateClick

/**
 * Shared empty-state layout: mascot icon, title, subtitle, and an
 * optional call-to-action button. Used for Home's first-launch state
 * and History's "no archived months yet" state.
 */
@Composable
fun EmptyStateView(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Icon(
            painter = painterResource(R.drawable.empty_history_blob),
            contentDescription = null,
            tint = androidx.compose.ui.graphics.Color.Unspecified,
            modifier = Modifier.size(96.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        if (actionLabel != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(24.dp))
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .pulsateClick(onActionClick)
                    .background(Mint, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                Text(
                    text = actionLabel,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    ),
                    color = Canvas
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun EmptyStateViewWithActionPreview() {
    BudgeeTheme {
        EmptyStateView(
            title = "Καλωσήρθες στο Ταμείο",
            subtitle = "Όρισε τον μηνιαίο σου προϋπολογισμό για να ξεκινήσεις.",
            actionLabel = "Ορισμός προϋπολογισμού",
            onActionClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun EmptyStateViewNoActionPreview() {
    BudgeeTheme {
        EmptyStateView(
            title = "Δεν υπάρχουν κλεισμένοι μήνες",
            subtitle = "Όταν κλείσει ο τρέχων μήνας, θα εμφανιστεί εδώ."
        )
    }
}