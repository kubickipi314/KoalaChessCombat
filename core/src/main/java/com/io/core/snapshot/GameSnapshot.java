package com.io.core.snapshot;

import java.util.List;

public record GameSnapshot(Long id, List<CharacterSnapshot> characterSnapshotList) {
}
