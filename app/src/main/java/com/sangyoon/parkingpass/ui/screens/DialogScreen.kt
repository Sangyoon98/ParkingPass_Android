package com.sangyoon.parkingpass.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sangyoon.parkingpass.R

@Composable
fun DialogScreen(
    context: Context,
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.hint_input_plate_number),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.button_cancel))
                    }
                    TextButton(
                        onClick = {
                            if (text.isEmpty()) {
                                Toast.makeText(context, context.getString(R.string.error_empty), Toast.LENGTH_SHORT).show()
                            } else {
                                onConfirmation(text)
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.button_add))
                    }
                }
            }
        }
    }
}