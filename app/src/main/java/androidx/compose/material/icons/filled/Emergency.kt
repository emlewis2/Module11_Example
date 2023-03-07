package androidx.compose.material.icons.filled

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Filled.Emergency: ImageVector
    get() {
        if (_emergency != null) {
            return _emergency!!
        }
        _emergency = materialIcon(name = "Filled.Emergency") {
            materialPath {
                moveTo(20.79f, 9.23f)
                lineToRelative(-2.0f, -3.46f)
                lineToRelative(-4.79f, 2.77f)
                lineToRelative(0.0f, -5.54f)
                lineToRelative(-4.0f, 0.0f)
                lineToRelative(0.0f, 5.54f)
                lineToRelative(-4.79f, -2.77f)
                lineToRelative(-2.0f, 3.46f)
                lineToRelative(4.79f, 2.77f)
                lineToRelative(-4.79f, 2.77f)
                lineToRelative(2.0f, 3.46f)
                lineToRelative(4.79f, -2.77f)
                lineToRelative(0.0f, 5.54f)
                lineToRelative(4.0f, 0.0f)
                lineToRelative(0.0f, -5.54f)
                lineToRelative(4.79f, 2.77f)
                lineToRelative(2.0f, -3.46f)
                lineToRelative(-4.79f, -2.77f)
                close()
            }
        }
        return _emergency!!
    }

private var _emergency: ImageVector? = null
