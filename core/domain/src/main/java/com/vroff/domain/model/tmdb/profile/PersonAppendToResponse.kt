package com.vroff.domain.model.tmdb.profile

import com.vroff.domain.model.tmdb.common.AppendableResponse

enum class PersonAppendToResponse(
    override val value: String,
) : AppendableResponse {
    EXTERNAL_IDS("external_ids"),
    COMBINED_CREDITS("combined_credits"),
}
